    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.Company;
import entity.User;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    private String username;
    private String password; 
    
    private Company companyToDisplayProjects; 

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        setCompanyToDisplayProjects((Company) ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyToDisplayProjects"));
        
    }
    
    public void login(ActionEvent event) throws IOException {
       
        try {
            User userEntity = userSessionBeanLocal.login(username,password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userEntity", userEntity);
            
        companyToDisplayProjects = (Company) event.getComponent().getAttributes().get("companyToDisplayProjects");
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToDisplayProjects", companyToDisplayProjects);
        System.out.println(companyToDisplayProjects.getUserId()); 
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        
        }
        catch(InvalidLoginCredentialException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credentials: " + ex.getMessage(), null));
        }
    }
    
    
    
    public void logout(javax.faces.event.ActionEvent event) throws IOException
    {
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
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

    public Company getCompanyToDisplayProjects() {
        return companyToDisplayProjects;
    }

    public void setCompanyToDisplayProjects(Company companyToDisplayProjects) {
        this.companyToDisplayProjects = companyToDisplayProjects;
    }
}
