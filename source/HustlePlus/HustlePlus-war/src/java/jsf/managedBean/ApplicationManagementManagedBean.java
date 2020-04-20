/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import entity.Application;
import entity.Project;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "applicationManagementManagedBean")
@ViewScoped
public class ApplicationManagementManagedBean implements Serializable {
    
     @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;
     
     private List<Application> applications;
     private Project viewProjectApplication; 

     


    /**
     * Creates a new instance of ApplicationManagementManagedBean
     */
    public ApplicationManagementManagedBean() {
        
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setApplications(applicationSessionBeanLocal.retrieveAllApplications());
  
    }
    
    /**
     * @param event
     */
    
    public void viewApplications(ActionEvent event) {
         setViewProjectApplication((Project) event.getComponent().getAttributes().get("viewProjectApplication"));
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewApplications.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Project getViewProjectApplication() {
        return viewProjectApplication;
    }

    public void setViewProjectApplication(Project viewProjectApplication) {
        this.viewProjectApplication = viewProjectApplication;
    }
    
    
    
    
}
