package th.cu.thesis.fur.api.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.thymeleaf.context.Context;

public class MailSenderUtil {
	
	private MimeMessage message;
	
	private String CONTENT_TYPE_TEXT_HTML = "text/html; charset=UTF-8";
	
	public MailSenderUtil(String stmpHost, String userName, String password) throws MessagingException, IOException {

		Properties props = System.getProperties();
		props.put("mail.smtp.host", stmpHost);
		props.put("mail.smtp.auth", "true");

		props.put("mail.mime.encodefilename", "true");
		props.put("mail.mime.decodefilename", "true");

		Authenticator auth = new StmpAuthenticator(userName, password);
		message = new MimeMessage(Session.getInstance(props, auth));
		
	}
	
	public MailSenderUtil(String stmpHost) throws MessagingException, IOException {
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", stmpHost);
		props.put("mail.smtp.port", "22");// for test
		props.put("mail.smtp.auth", "false");
		props.put("mail.mime.encodefilename", "true");
		props.put("mail.mime.decodefilename", "true");
		
		message = new MimeMessage(Session.getInstance(props, null));
	}
	
	public void send(String from, String to, String subject, String content)throws MessagingException {
		
		try {						
			if ((from == null) || "".equals(from.trim())) {
				throw new NullPointerException("from address must not be null or empty");
			}
	
			if ((to == null) || "".equals(to.trim())) {
				throw new NullPointerException("to address must not be null or empty");
			}
				
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(content, CONTENT_TYPE_TEXT_HTML);

			Transport.send(message);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void send(String from, List<String> to, String subject, String content)throws MessagingException {
		
		try {
			if ((from == null) || "".equals(from.trim())) {
				throw new NullPointerException("from address must not be null or empty");
			}
	
			if ((to == null)) {
				throw new NullPointerException("to address must not be null or empty");
			}
			
			InternetAddress[] listTo = new InternetAddress[to.size()];
			
			for(int i=0;i<listTo.length;i++){
				listTo[i] = new InternetAddress(to.get(i));
			}			
	
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, listTo);
			message.setSubject(subject, "UTF-8");
			message.setContent(content, CONTENT_TYPE_TEXT_HTML);			
	
			Transport.send(message);
	
			
		} catch (Exception e) {
			
		}
		
	}
	
	static class StmpAuthenticator extends javax.mail.Authenticator {

		private String username;

		private String password;

		public StmpAuthenticator(String username, String password) {

			this.username = username;
			this.password = password;
		}

		@Override
		public PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(username, password);
		}
	}
	
	

}
