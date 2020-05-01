/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import com.mailsender.MailSender;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "emailManagedBean")
@SessionScoped
public class EmailManagedBean implements Serializable {
    
    private String fromMail; 
    private String toMail; 
    private String username;
    private String password; 
    private String subject;
    private String message; 

    /**
     * Creates a new instance of EmailManagedBean
     */
    public EmailManagedBean() {
    }
    
    public void send() {
        MailSender mailSender = new MailSender();
        mailSender.sendMail(fromMail, toMail, username, password, subject, message);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email successfully sent to student!", null));
    }
    
    public void sendEmailToUser() {
        MailSender mailSender = new MailSender();
        mailSender.sendMail("hustlePlusEst2020@gmail.com", toMail, "hustlePlusEst2020", "P@ssword1234", subject, message);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email successfully sent to user!", null));
    }
    
    public void sendEmailToAdmin() {
        MailSender mailSender = new MailSender();
        mailSender.sendMail(fromMail, "hustlePlusEst2020@gmail.com", username, password, subject, message);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email successfully sent to admin! Please give us 2-3 working days to respond.", null));
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    
    
}
