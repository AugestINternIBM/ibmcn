package com.ibm.contract.client;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.ibm.www.rules.decisionservice.ContractRulesDeployment.ContractOperation.Notification;

public class JavaEmail {

	Properties emailProperties;
	Session mailSession;
	MimeMessage emailMessage;

	public JavaEmail (){
		setMailServerProperties();
	}

	public void setMailServerProperties() {

		String emailPort = "587";//gmail's smtp port

		emailProperties = System.getProperties();
		emailProperties.put("mail.smtp.port", emailPort);
		emailProperties.put("mail.smtp.auth", "true");
		emailProperties.put("mail.smtp.starttls.enable", "true");
		emailProperties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");

	}

	public void createEmailMessage(Notification notification) throws AddressException,
			MessagingException {
		String toEmails =notification.getTarget();
		String ccEmail = notification.getCc();
		String emailSubject = notification.getTopic();
		String emailBody = notification.getBody();

		mailSession = Session.getDefaultInstance(emailProperties, null);
		emailMessage = new MimeMessage(mailSession);

		emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmails));
		emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccEmail));
		emailMessage.setSubject(emailSubject);
		emailMessage.setContent(emailBody, "text/html");
	}

	public void sendEmail() throws AddressException, MessagingException {

		String emailHost = "smtp.gmail.com";
		String fromUser = "ibmcnsender";//just the id alone without @gmail.com
		String fromUserEmailPassword = "135791113M";

		Transport transport = mailSession.getTransport("smtp");

		transport.connect(emailHost, fromUser, fromUserEmailPassword);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
		transport.close();
		System.out.println("Email sent successfully.");
	}
	
}