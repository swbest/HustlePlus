/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import entity.Company;
import entity.Milestone;
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
import util.exception.MilestoneNotFoundException;
import util.exception.ProjectNotFoundException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getMilestonesManagedBean")
@ViewScoped

public class GetMilestonesManagedBean implements Serializable {

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;

        private List<Milestone> milestonesForSelectedCompany; 
        private Company companyToDisplayMilestones; 
        
        private Project projectToDisplayMilestones;
        private List<Milestone> milestonesForSelectedProject; 


    /**
     * Creates a new instance of getMilestonesManagedBean
     */
    public GetMilestonesManagedBean() {
    }
    
     @PostConstruct
    public void postConstruct()
    {
       
        //companyToDisplayMilestones = (Company)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyToDisplayMilestones");
        milestonesForSelectedCompany = (List<Milestone>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("milestonesForSelectedCompany");
        milestonesForSelectedProject = (List<Milestone>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("milestonesForSelectedProject");
//        for (Project project:projects) {
//            selectItems.add(new SelectItem(project));
//        }
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("milestonesProject", projects); 
   
    }
    
    
         public void retrieveMilestonesForCompany(ActionEvent event) {
        System.out.println("CALLED");
        try {
            setCompanyToDisplayMilestones((Company) event.getComponent().getAttributes().get("company"));
       //((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToDisplayMilestones", companyToDisplayMilestones); 
        
        milestonesForSelectedCompany = milestoneSessionBeanLocal.retrieveMilestonesByCompany(getCompanyToDisplayMilestones().getUserId());
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("milestonesForSelectedCompany", milestonesForSelectedCompany); 
        System.out.println("CALLED2"); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/milestoneManagement.xhtml");

        } catch (ProjectNotFoundException | IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving milestones: " + ex.getMessage(), null));
        }
        
    }
         
         public void retrieveMilestonesForProject(ActionEvent event) {
            try {
                 projectToDisplayMilestones = (Project) event.getComponent().getAttributes().get("projectToView");
                 milestonesForSelectedProject = milestoneSessionBeanLocal.retrieveMilestonesByProject(projectToDisplayMilestones.getProjectId()); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("milestonesForSelectedProject", milestonesForSelectedProject); 
                 FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/milestoneForProject.xhtml");
         } catch (IOException | MilestoneNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving milestones: " + ex.getMessage(), null));
         }
         }

    public List<Milestone> getMilestonesForSelectedCompany() {
        return milestonesForSelectedCompany;
    }

    public void setMilestonesForSelectedCompany(List<Milestone> milestonesForSelectedCompany) {
        this.milestonesForSelectedCompany = milestonesForSelectedCompany;
    }

    public Company getCompanyToDisplayMilestones() {
        return companyToDisplayMilestones;
    }

    public void setCompanyToDisplayMilestones(Company companyToDisplayMilestones) {
        this.companyToDisplayMilestones = companyToDisplayMilestones;
    }

    public Project getProjectToDisplayMilestones() {
        return projectToDisplayMilestones;
    }

    public void setProjectToDisplayMilestones(Project projectToDisplayMilestones) {
        this.projectToDisplayMilestones = projectToDisplayMilestones;
    }

    public List<Milestone> getMilestonesForSelectedProject() {
        return milestonesForSelectedProject;
    }

    public void setMilestonesForSelectedProject(List<Milestone> milestonesForSelectedProject) {
        this.milestonesForSelectedProject = milestonesForSelectedProject;
    }
    
    
    
    
}
