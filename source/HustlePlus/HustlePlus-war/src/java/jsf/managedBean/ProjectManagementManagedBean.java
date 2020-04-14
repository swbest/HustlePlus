/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.SkillSessionBeanLocal;
import entity.Application;
import entity.Company;
import entity.Milestone;
import entity.Project;
import entity.Review;
import entity.Skill;
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
import org.primefaces.event.UnselectEvent;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.DeleteProjectException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.ProjectNotFoundException;
import util.exception.SkillNameExistsException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "projectManagementManagedBean")
@ViewScoped
public class ProjectManagementManagedBean implements Serializable {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

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
    private List<Skill> skills;
    private Skill newSkill; 
    private Project filteredProjects;
    
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
        setSkills(skillSessionBeanLocal.retrieveAllSkills());

        
    }
    
    public void viewProjectDetails(ActionEvent event) throws IOException {
        Long projectIdToView = (Long)event.getComponent().getAttributes().get("projectId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectIdToView", projectIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProjectDetails.xhtml");    
    }  
    
     public void createNewProject(ActionEvent event) {
        
        try {
            System.out.println("test");
            Company companyTagged = (Company) event.getComponent().getAttributes().get("companyTagged");
            System.out.println("Id:" + companyTagged.getUserId());
            //int i = 1;
            //long l = i;
            Long projectId = projectSessionBeanLocal.createNewProject(newProject, companyTagged.getUserId());
            System.out.println("PMMB1");
            projects.add(projectSessionBeanLocal.retrieveProjectByProjectId(projectId)); 
            System.out.println("PMMB2");
            newProject = new Project();
            System.out.println("PMM3");
            setMilestoneIdsNew(null); 
            System.out.println("PMM4");
            setCompanyId(null); 
            System.out.println("PMM5");
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New project created successfully (Project ID: " + projectId + ")", null));
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
     
     public void addNewSkill(ActionEvent event) {
         try {
             Long skillId = skillSessionBeanLocal.createNewSkill(getNewSkill()); 
             //getSkills().add(skillSessionBeanLocal.retrieveSkillBySkillId(skillId));
             setNewSkill(new Skill()) ; 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New skill created successfully (Skill ID: " + skillId + ")", null));
             
       // } catch (SkillNotFoundException ex) {
        //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new skill: Skill not found", null));
         } catch (SkillNameExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new skill: Skill name exists", null));
         } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new skill: " + ex.getMessage(), null));
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
    
     public void onItemUnselect(UnselectEvent event) {
         FacesContext context = FacesContext.getCurrentInstance();
         FacesMessage msg = new FacesMessage();
         msg.setSummary("Item unselected: " + event.getObject().toString());
         msg.setSeverity(FacesMessage.SEVERITY_INFO);
         context.addMessage(null,msg); 
         
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Skill getNewSkill() {
        return newSkill;
    }

    public void setNewSkill(Skill newSkill) {
        this.newSkill = newSkill;
    }

    public Project getFilteredProjects() {
        return filteredProjects;
    }

    public void setFilteredProjects(Project filteredProjects) {
        this.filteredProjects = filteredProjects;
    }
    
    
    
    
    
    
    
     
     
}
