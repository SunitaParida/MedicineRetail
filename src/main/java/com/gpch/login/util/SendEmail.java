package com.gpch.login.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
   public static void main(String[] args) {
      // Put recipientâ€™s address
      String to = "asunita97p@gmail.com";

      // Put senderâ€™s address
      String from = "betacentauri2020@gmail.com";
      final String username = "betacentauri2020@gmail.com";//username generated by Mailtrap
      final String password = "beta@1234";//password generated by Mailtrap

      // Paste host address from the SMTP settings tab in your Mailtrap Inbox
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");//itâ€™s optional in Mailtrap  
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
    }
         });
      try {
    // Create a default MimeMessage object.
    Message message = new MimeMessage(session);
 
    // Set From: header field 
    message.setFrom(new InternetAddress(from));
 
    // Set To: header field
    message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
 
    // Set Subject: header field
    message.setSubject("Beta Centauri Training");
 
    // Put the content of your message
    message.setText("Dear Team,This is outr first mail integration.");

    // Send message
    Transport.send(message);

    System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
   
   public void sendMail(String to,String from,String password,String host,String port,String subject,String body)
   {
	   
	      final String username = from;
	     
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");//itâ€™s optional in Mailtrap  
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", port);// use one of the options in the SMTP settings tab in your Mailtrap Inbox

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);
	    }
	         });

	      try {
	    // Create a default MimeMessage object.
	    Message message = new MimeMessage(session);
	 
	    // Set From: header field 
	    message.setFrom(new InternetAddress(from));
	 
	    // Set To: header field
	    message.setRecipients(Message.RecipientType.TO,
	               InternetAddress.parse(to));
	 
	    // Set Subject: header field
	    message.setSubject(subject);
	 
	    // Put the content of your message
	    message.setContent(body, "text/html");
	   

	    // Send message
	    Transport.send(message);

	    System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	         throw new RuntimeException(e);
	      } 
   }
}