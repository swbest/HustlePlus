/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Company;
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
import util.exception.ProjectNotFoundException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getAllProjectsManagedBean")
@ViewScoped
public class GetAllProjectsManagedBean implements Serializable {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    

    
    private Company companyToView; 
    private List<Project> projectsForCompany; 
    /**
     * Creates a new instance of GetAllProjectsManagedBean
     */
    public GetAllProjectsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
       
        setCompanyToView((Company) ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyToView"));
        setProjectsForCompany((List<Project>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectsForCompany"));
    
    }
    
    public void viewProjectByCompany(ActionEvent event) {
        
        try {  
        
        setCompanyToView((Company) event.getComponent().getAttributes().get("companyToView"));
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToView", getCompanyToView());
        
        
        projectsForCompany = projectSessionBeanLocal.retrieveProjectsByCompany(companyToView.getUserId());
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectsForCompany", projectsForCompany); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectManagement.xhtml");
        //setProjectsOfCompany(projectSessionBeanLocal.retrieveProjectsByCompany(companyToView.getUserId()));
      
        
        //FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/myProject.xhtml");
           
        }  
    //catch (ProjectNotFoundException ex) {
    //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
   // } 
        catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    } catch (ProjectNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving projects: " + ex.getMessage(), null));
    }
    } 

    public Company getCompanyToView() {
        return companyToView;
    }

    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }

    public List<Project> getProjectsForCompany() {
        return projectsForCompany;
    }

    public void setProjectsForCompany(List<Project> projectsForCompany) {
        this.projectsForCompany = projectsForCompany;
    }
    
    
    
    
}
