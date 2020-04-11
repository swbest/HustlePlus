/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Application;
import entity.Milestone;
import entity.Project;
import entity.Review;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.DeleteProjectException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "projectManagementManagedBean")
@ViewScoped
public class ProjectManagementManagedBean implements Serializable {

    @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    @Inject
    private ViewProjectManagedBean viewProjectManagedBean;
    
    private List<Project> projects;
    
    
    private List<Long> milestoneIdsNew ; 
    private Long companyId; 
    private List<Application> applications;
    private List<Review> reviews;
    private List<Milestone> milestones; 
    private Project newProject ; 
    
    private Project selectedProjectToUpdate;
    private List<Long>milestoneIdsUpdate;
    /**
     * Creates a new instance of projectManagementManagedBean
     */
    public ProjectManagementManagedBean() {
        newProject = new Project() ; 
    }
    
    @PostConstruct
    public void postConstruct() {
        
        setProjects(projectSessionBeanLocal.retrieveAllProjects());
        setMilestones(milestoneSessionBeanLocal.retrieveAllMilestones());
        setReviews(reviewSessionBeanLocal.retrieveAllReviews());
        setApplications(applicationSessionBeanLocal.retrieveAllApplications());
        
    }
    
    public void viewProjectDetails(ActionEvent event) throws IOException {
        Long projectIdToView = (Long)event.getComponent().getAttributes().get("projectId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectIdToView", projectIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProjectDetails.xhtml");    
    }  
    
     public void createNewProject(ActionEvent event) {
        
        try {
            Long projectId = projectSessionBeanLocal.createNewProject(newProject, getCompanyId());
            getProjects().add(projectSessionBeanLocal.retrieveProjectByProjectId(projectId)); 
            newProject = new Project();
            setMilestoneIdsNew(null); 
            setCompanyId(null); 
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New project created successfully (Milestone ID: " + projectId + ")", null));
            
        } catch (ProjectNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new project: Project not found", null));
        } catch (CompanyNotVerifiedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company has not been verified", null));
        } catch (CompanySuspendedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company has been suspended", null));
        } catch (ProjectNameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Project name exists", null));
        }catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company does not exist", null));
        }catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: " + ex.getMessage(), null));
        }
    }
     
     public void doUpdateProject(ActionEvent event) {
         setSelectedProjectToUpdate((Project) event.getComponent().getAttributes().get("projectToUpdate"));
         setMilestoneIdsUpdate(new ArrayList<>());
         
         for (Milestone milestone: getSelectedProjectToUpdate().getMilestones()) {
             getMilestoneIdsUpdate().add(milestone.getMilestoneId());
         }
     }

     public void updateProject(ActionEvent event) {
         try {
             projectSessionBeanLocal.updateProject(getSelectedProjectToUpdate(), getCompanyId());
             getSelectedProjectToUpdate().getMilestones().clear(); 
             for (Milestone m: getMilestones()) {
                 if (getMilestoneIdsUpdate().contains(m.getMilestoneId())) {
                     getSelectedProjectToUpdate().getMilestones().add(m);
                 } 
             }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Project updated successfully", null));
   
         } catch (ProjectNotFoundException | CompanyNotFoundException | UpdateProjectException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating project: " + ex.getMessage(), null));
         }
     }
     
     public void deleteProject(ActionEvent event) {
         try {
             Project projectToDelete = (Project) event.getComponent().getAttributes().get("projectToDelete"); 
             projectSessionBeanLocal.deleteProject(projectToDelete.getProjectId());
             getProjects().remove(projectToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Project deleted successfully", null));
         } catch(ProjectNotFoundException | DeleteProjectException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting company account: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
     
     public ViewProjectManagedBean getViewProjectManagedBean() {
         return viewProjectManagedBean;
     }
     
     public void setViewProjectManagedBean(ViewProjectManagedBean viewProjectManagedBean) {
         this.viewProjectManagedBean = viewProjectManagedBean;
     }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Long> getMilestoneIdsNew() {
        return milestoneIdsNew;
    }

    public void setMilestoneIdsNew(List<Long> milestoneIdsNew) {
        this.milestoneIdsNew = milestoneIdsNew;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public Project getSelectedProjectToUpdate() {
        return selectedProjectToUpdate;
    }

    public void setSelectedProjectToUpdate(Project selectedProjectToUpdate) {
        this.selectedProjectToUpdate = selectedProjectToUpdate;
    }

    public List<Long> getMilestoneIdsUpdate() {
        return milestoneIdsUpdate;
    }

    public void setMilestoneIdsUpdate(List<Long> milestoneIdsUpdate) {
        this.milestoneIdsUpdate = milestoneIdsUpdate;
    }

    public Project getNewProject() {
        return newProject;
    }

    public void setNewProject(Project newProject) {
        this.newProject = newProject;
    }
    
    
     
     
}
