package com.ibm.contract.client;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFileChooser;
import javax.xml.rpc.ServiceException;

import com.ibm.contract.parser.Feedback;
import com.ibm.contract.parser.FeedbackParser;
import com.ibm.contract.parser.Parser;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Contract;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.ContractOperationDecisionService_ServiceLocator;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.ContractOperationRequest;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.ContractOperationResponse;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.ContractRulesDeploymentContractOperationBindingStub;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.param.Contracts;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.param.Notifications;

public class ClientApplication {

	private static Contracts contractArrayList = new Contracts();
	private static Notifications notificationList = new Notifications();
	private static JavaEmail javaEmail = new JavaEmail();
	private static String feedbackPath;
	private static String fcPath, outPutFilePath;
	private static JFileChooser fileChooser;

	/**
	 * @param outPutFilePath
	 *            the outPutFilePath to set
	 */
	public static void setOutPutFilePath(String outPutFilePath) {
		ClientApplication.outPutFilePath = outPutFilePath;
	}

	/**
	 * @param feedbackPath
	 *            the feedbackPath to set
	 */
	public static void setFeedbackPath(String feedbackPath) {
		ClientApplication.feedbackPath = feedbackPath;
	}

	/**
	 * @param fcPath
	 *            the fcPath to set
	 */
	public static void setFcPath(String fcPath) {
		ClientApplication.fcPath = fcPath;
	}

	private static List<Contract> parseExcel() throws AddressException, MessagingException {
		Parser parser = new Parser();
		FeedbackParser feedbackParser = new FeedbackParser();
		Map<String, Feedback> feedbackMap = feedbackParser.getContractListFromExcel(feedbackPath);

		List<Contract> contractsList = parser.getContractListFromExcel(fcPath, feedbackMap);
		return contractsList;
	}

	public void invoqueRequest() throws ServiceException, RemoteException, AddressException, MessagingException {
		List<Contract> contractsList = parseExcel();

		Contract[] input = new Contract[contractsList.size()];
		Notification[] output = new Notification[input.length];
		for (int i = 0; i < input.length; i++) {
			input[i] = contractsList.get(i);
			output[i] = new Notification(new String(), new String(), new String(), "", "");
		}

		contractArrayList.setContracts(input);
		notificationList.setNotifications(output);

		ContractOperationRequest request = new ContractOperationRequest("1", contractArrayList, notificationList);

		ContractRulesDeploymentContractOperationBindingStub stub = new ContractRulesDeploymentContractOperationBindingStub();

		ContractOperationDecisionService_ServiceLocator locater = new ContractOperationDecisionService_ServiceLocator();
		locater.setContractRulesDeploymentContractOperationPortEndpointAddress(getConfig("host"));
		stub = (ContractRulesDeploymentContractOperationBindingStub) locater.getPort(stub.getClass());

		ContractOperationResponse response = new ContractOperationResponse("1", notificationList);

		response = stub.contractOperation(request);

		notificationList = response.getNotifications();
		output = notificationList.getNotifications();
		writeResultToFile(output, outPutFilePath);
		for (int i = 0; i < output.length; i++) {
			if (!output[i].getTarget().equals("")) {
				javaEmail.createEmailMessage(output[i]);
				javaEmail.sendEmail();
			}
		}
	}

	private void writeResultToFile(Notification[] output, String filePath) {

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
			for (int i = 0; i < output.length; i++) {
				if (!output[i].getTarget().equals("")) {
					writer.write("to : " + output[i].getTarget());
					writer.write(System.lineSeparator());
					writer.write("cc : " + output[i].getCc());
					writer.write(System.lineSeparator());
					writer.write("subject : " + output[i].getTopic());
					writer.write(System.lineSeparator());
					writer.write("body : ");
					String[] str = output[i].getBody().split("\n");
					int j = 0;
					while (j < str.length) {
						writer.write(str[j++]);
						writer.write(System.lineSeparator());

					}
					writer.write(System.lineSeparator());
					writer.write(System.lineSeparator());

				}
			}
			writer.close();
		} catch (IOException ex) {

		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				/* ignore */}
		}

	}

	public static String getConfig(String io) {
		Properties prop = new Properties();
		String fileName = "app.config";
		InputStream is = null;
		try {
			is = new FileInputStream(fileName);
		} catch (FileNotFoundException ex) {
			fileChooser = new JFileChooser();
		}
		try {
			prop.load(is);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return (prop.getProperty(io));
	}
}
