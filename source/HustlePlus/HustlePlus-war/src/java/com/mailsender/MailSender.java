/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsender;

import java.util.Date;
import java.util.Properties;
import javax.mail.*; 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Nurhidayah
 */
public class MailSender 
{
 
    private String smtpAuthUser;
    private String smtpAuthPassword;
    
    
    
    public MailSender()
    {
    }

    
    
    public MailSender(String smtpAuthUser, String smtpAuthPassword)
    {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }
    //Username: hustlepluscompany123 Password: P@ssword1234 
    public void sendMail(String fromEmail, String toEmail, String username, String password, String subject, String message) {
        
     
            Properties props = new Properties();
            props.put("mail.smtp.user", fromEmail);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");            
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false"); 
            

            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props,auth);
            session.setDebug(true);            
            
            try {
            Message msg = new MimeMessage(session);
         
                msg.setFrom(new InternetAddress(fromEmail));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
                msg.setSubject(subject);
                msg.setContent(message, "text/html");
                
                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);
                
                Transport transport = session.getTransport("smtps");
                transport.connect("smtp.gmail.com", 465, username, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close(); 
                System.out.println("Done EMAIL");
                
               
        } catch (Exception e)  {
            e.printStackTrace();  
    }
}

            
    
}
