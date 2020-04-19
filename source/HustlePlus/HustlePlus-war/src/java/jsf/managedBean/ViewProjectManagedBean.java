/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Project;
import entity.Skill;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author amanda
 */
@Named(value = "viewProjectManagedBean")
@RequestScoped
public class ViewProjectManagedBean implements Serializable {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    private Project projectToView;
   private List<Project> projectsToView; 
   private String displayStatus; 
   private List<Skill> skills; 

    /**
     * Creates a new instance of ViewProjectManagedBean
     */
    public ViewProjectManagedBean() {
        projectToView = new Project();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        
    /*  Company company = (Company) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("companyToView");
      
      try {
        setProjectsToView(projectSessionBeanLocal.retrieveProjectsByCompany(company.getUserId()));

    } catch (ProjectNotFoundException ex)  {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
    }*/
        
    }
    
       /* public void viewProjectByCompany(ActionEvent event) {
        
        try {  
        System.out.println("VPBC1");
        Company company = (Company) event.getComponent().getAttributes().get("companyProjects");
        System.out.println(company.getUserId());
        setProjectsOfCompany(projectSessionBeanLocal.retrieveProjectsByCompany(company.getUserId()));
        if (projectsOfCompany.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("notnull");
            }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectManagement.xhtml");
                System.out.println("VPBC2");
                        if (projectsOfCompany.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("notnull");
            }
        

        }  
    catch (ProjectNotFoundException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    } */

    /**
     * @return the projectToView
     */
    
    public void viewApplications() {
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewApplications.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }
    
        public void viewTeam() {
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewTeam.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }
        
        
    public void reviews() {
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviews.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }    public Project getProjectToView() {
        return projectToView;
    }
    
    public void setStatusText() {
        
        if (projectToView.getIsFinished()) {
            System.out.println("Called");
            setDisplayStatus("Project Completed"); 
        } else {
            System.out.println("Not called");
            setDisplayStatus("Project Not Completed"); 
        }
        
    }
        
     
    
    public void viewMilestones() {
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewMilestones.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }
    /**
     * @param projectToView the projectToView to set
     */
    public void setProjectToView(Project projectToView) {
        this.projectToView = projectToView;
    }

    public List<Project> getProjectsToView() {
        return projectsToView;
    }

    public void setProjectsToView(List<Project> projectsToView) {
        this.projectsToView = projectsToView;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    
    
    
    
    
    
    

   
    
    
}
