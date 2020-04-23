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
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getApplicationForProjectManagedBean")
@ViewScoped
public class getApplicationForProjectManagedBean implements Serializable {

    @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;
    
    
    
    private List <Application> applicationsForProject;
    private Project projectToDisplayApplications; 

    /**
     * Creates a new instance of getApplicationForProjectManagedBean
     */
    public getApplicationForProjectManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        applicationsForProject = (List<Application>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("applicationsForProject");
    }
    
     public void retrieveApplicationsForProject(ActionEvent event) {
            try {
                 projectToDisplayApplications = (Project) event.getComponent().getAttributes().get("projectToViewApp");
                 applicationsForProject = applicationSessionBeanLocal.retrieveApplicationByProject(projectToDisplayApplications.getProjectId()); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("applicationsForProject", applicationsForProject); 
                 FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewApplications.xhtml");
         } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving milestones: " + ex.getMessage(), null));
         }
         }

    public List <Application> getApplicationsForProject() {
        return applicationsForProject;
    }

    public void setApplicationsForProject(List <Application> applicationsForProject) {
        this.applicationsForProject = applicationsForProject;
    }

    public Project getProjectToDisplayApplications() {
        return projectToDisplayApplications;
    }

    public void setProjectToDisplayApplications(Project projectToDisplayApplications) {
        this.projectToDisplayApplications = projectToDisplayApplications;
    }
    
    
    
    
}
