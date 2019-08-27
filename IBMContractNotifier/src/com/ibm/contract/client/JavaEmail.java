package com.ibm.contract.client;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ibm.contract.parser.ExcelParser;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification;

public class JavaEmail {

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;
	ExcelParser ep = new ExcelParser();

	public JavaEmail() {
		setMailServerProperties();
	}

	public void setMailServerProperties() {

		String emailPort = "587";// gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

	}

	public void createEmailMessage(Notification notification) throws AddressException, MessagingException {
		String[] toEmails = notification.getTarget().split(",");
		String[] ccEmails = notification.getCc().split(",");
		String emailSubject = notification.getTopic();
		String emailBody = notification.getBody();

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);
		for (int i = 0; i < toEmails.length; i++)
			emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails[i]));
		for (int i = 0; i < ccEmails.length; i++)
			emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmails[i]));
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");
	}

	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = ep.email;
		int index = fromUser.indexOf("@");
		fromUser = fromUser.substring(0, index);
		String fromUserEmailPassword = ep.password;

		Transport transport = mailSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
	
	}

}