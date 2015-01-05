package com.ritchie.email;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;


/**
 * http://stackoverflow.com/questions/1990454/using-javamail-to-connect-to-gmail-smtp-server-ignores-specified-port-and-tries
 */
@Stateless
public class SendEmailService {
	
	private Logger logger = Logger.getLogger(SendEmailService.class.getName());

//	private final String GMAIL_HOST = "smtp.gmail.com";
	private final String ZOHO_HOST = "smtp.zoho.com";
	private final String SSL_PORT = "465"; //change to 897 for TLS
	private final String NO_REPLY_EMAIL = "";
	private final String USERNAME = "";
	private final String PASSWORD = "";
	
	/*
	 * You would not normally execute this code in the same thread that is calling it, but for ease of demonstration
	 */
	public void sendEmail() {
		
		final String ccEmail = "";
		final String recipientEmail = "";
		final String title = "Demo";
		final String message = "Message Sent via JavaMail";

		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", ZOHO_HOST);
		props.setProperty("mail.smtp.port",SSL_PORT);
		props.setProperty("mail.smtp.startssl.enable", "true");
		props.setProperty("mail.smtps.auth", "true");

		//close connection upon quit being sent
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, null);

		// -- Create a new message --
		final MimeMessage msg = new MimeMessage(session);

		// -- Set the FROM and TO fields --
		try {
			msg.setFrom(new InternetAddress(NO_REPLY_EMAIL));

			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientEmail, false));

			if (ccEmail != null && ccEmail.length() > 0) {
				msg.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(ccEmail, false));
			}

			msg.setSubject(title);
			msg.setText(message, "utf-8", "html");
			msg.setSentDate(new Date());

			Transport transport = session.getTransport("smtps"); //this means you do not need socketFactory properties

			transport.connect(ZOHO_HOST, USERNAME, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			logger.info("Invite successfully sent to " + recipientEmail);

		} catch (AddressException e) {
			logger.log(Level.SEVERE, "Invalid recipient address", e);
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Failed to send email", e);
		}

	}

}
