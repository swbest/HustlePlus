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
import javax.servlet.http.HttpSession;
import org.primefaces.event.UnselectEvent;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.DeleteProjectException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.ProjectNotFoundException;
import util.exception.SkillNotFoundException;
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
    
    @Inject
    private LoginManagedBean loginManagedBean;
    
    @Inject
    private GetAllProjectsManagedBean getAllProjectsManagedBean; 
    
    private List<Project> projects;
    
    private List<Project> projectsOfCompany; 
    private Company companyToView; 
    
    private List<Long> skillIdsToAddToNewProject;
    private List<Long> skillIdsToAddToSelectedProjectToUpdate;
    
    private Project projectDeleteSkill;
    
    private Company companyToDisplayProject; 
    
    

    
    
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
    
    private boolean statusValue;
    
    private String displayStatus; 

    private Project projectToView; //to display milestones
    private Project projectToViewMS ; //to display milestones 
    

    
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
        
        projectDeleteSkill = (Project)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectDeleteSkill");
        companyToView = (Company) ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyToView");
        projectToViewMS = (Project)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectToViewMS");
    }
    
    public void viewProjectDetails(ActionEvent event) throws IOException {
        Long projectIdToView = (Long)event.getComponent().getAttributes().get("projectId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectIdToView", projectIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProjectDetails.xhtml");    
    }  
    

    
    
    public void viewProjectByCompany(ActionEvent event) {
        
        try {  
        
         companyToView = (Company) event.getComponent().getAttributes().get("companyToView");
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToView", companyToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectManagement.xhtml");
        //setProjectsOfCompany(projectSessionBeanLocal.retrieveProjectsByCompany(companyToView.getUserId()));
      
        
        //FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/myProject.xhtml");
           
        }  
    //catch (ProjectNotFoundException ex) {
    //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
   // } 
        catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    } 
    } 
    

    
     public void createNewProject(ActionEvent event) {
        
        try {
            System.out.println("test");
            Company companyTagged = (Company) event.getComponent().getAttributes().get("companyTagged");
            System.out.println("Id:" + companyTagged.getUserId());
            //int i = 1;
            //long l = i;
            Long projectId = projectSessionBeanLocal.createNewProject(newProject, companyTagged.getUserId(), skillIdsToAddToNewProject );

            System.out.println("PMMB1");
            projects.add(projectSessionBeanLocal.retrieveProjectByProjectId(projectId)); 
            System.out.println("PMMB2");
            //newProject = new Project();
            System.out.println("PMM3");
            //setMilestoneIdsNew(null); 
            System.out.println("PMM4");
            //setCompanyId(null); 
            System.out.println("PMM5");
            
            List<Project> projectList = loginManagedBean.getProjectsToDisplay();
            Project p = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
            projectList.add(p);
            loginManagedBean.setProjectsToDisplay(projectList);

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
        } catch (SkillNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new project: Skill not found", null));
        }
    }
    
     public void deleteSkillFromProject(ActionEvent event) {
        try {
        projectDeleteSkill = (Project) event.getComponent().getAttributes().get("projectToDeleteSkill"); 
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectDeleteSkill", projectDeleteSkill);
        
        System.out.println(getProjectDeleteSkill().getProjectId()); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/skillsOfProject.xhtml");

        } catch (IOException ex) {
            
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
             System.out.println("********** updateProject");
             System.out.println(getSelectedProjectToUpdate().getProjectName());
             projectSessionBeanLocal.updateProject(getSelectedProjectToUpdate(), skillIdsToAddToSelectedProjectToUpdate);
              selectedProjectToUpdate.setProjectName(selectedProjectToUpdate.getProjectName());
             getSelectedProjectToUpdate().getMilestones().clear(); 
             for (Milestone m: getMilestones()) {
                 if (getMilestoneIdsUpdate().contains(m.getMilestoneId())) {
                     getSelectedProjectToUpdate().getMilestones().add(m);
                 } 
             }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Project updated successfully", null));
   
         } catch (ProjectNotFoundException | SkillNotFoundException | UpdateProjectException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating project: " + ex.getMessage(), null));
         }
     }
     
     public void removeSkillFromProject(ActionEvent event) {
         
         try {
         Project projectToRemoveFromSkill = (Project) event.getComponent().getAttributes().get("projectToRemoveFromSkill"); 
         Skill skillToRemoveFromProject = (Skill) event.getComponent().getAttributes().get("skillToDelete"); 
         
         projectToRemoveFromSkill.getSkills().remove(skillToRemoveFromProject);
         skillToRemoveFromProject.getProjects().remove(projectToRemoveFromSkill);
        
         projectSessionBeanLocal.disassociateProjectSkill(projectToRemoveFromSkill.getProjectId(), skillToRemoveFromProject.getSkillId());
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Skill successfully removed from project!", null));
     } catch (ProjectNotFoundException | SkillNotFoundException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while removing skill: " + ex.getMessage(), null));
     }
     }

     public void deleteProject(ActionEvent event) {
         try {
             Project projectToDelete = (Project) event.getComponent().getAttributes().get("projectToDelete"); 
             
             List<Project> p = getAllProjectsManagedBean.getProjectsForCompany(); 
             p.remove(projectToDelete);
             getAllProjectsManagedBean.setProjectsForCompany(p);
             
            List<Project> projectList = loginManagedBean.getProjectsToDisplay();
            projectList.remove(projectToDelete);
            loginManagedBean.setProjectsToDisplay(projectList);
 
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
     
     public void addMessageForStatus(){
         String summary = getStatusValue() ? "Project Completed" : "Project Not Completed";
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary)); 
     }
     
     public void changeProjectStatus() {
         if (getStatusValue() == true) {
             System.out.println("trueCPS");
             selectedProjectToUpdate.setIsFinished(true);
             System.out.println(selectedProjectToUpdate.getIsFinished());
         } else {
             System.out.println("falseCPS");
             selectedProjectToUpdate.setIsFinished(false);
             System.out.println(selectedProjectToUpdate.getIsFinished());
         }
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
     
     
    
     public void onItemUnselect(UnselectEvent event) {
         FacesContext context = FacesContext.getCurrentInstance();
         FacesMessage msg = new FacesMessage();
         msg.setSummary("Item unselected: " + event.getObject().toString());
         msg.setSeverity(FacesMessage.SEVERITY_INFO);
         context.addMessage(null,msg); 
         
     }
     
     public void directToProjects(ActionEvent event) {
         try{
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectManagement.xhtml");
     } catch (IOException ex) {
         
     }
     }
     
     //in the view tab in projectManagement 
         public void viewMilestones(ActionEvent event) {
       try {
            setProjectToViewMS((Project) event.getComponent().getAttributes().get("projectToViewMilestone"));
       // ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectToViewMS", getProjectToViewMS());
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewMilestones.xhtml");
       } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
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

    public List<Project> getProjectsOfCompany() {
        return projectsOfCompany;
    }

    public void setProjectsOfCompany(List<Project> projectsOfCompany) {
        this.projectsOfCompany = projectsOfCompany;
    }

    public boolean getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(boolean statusValue) {
        this.statusValue = statusValue;
    }

    public Company getCompanyToView() {
        return companyToView;
    }

    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }


    
        public List<Long> getSkillIdsToAddToNewProject() {
        return skillIdsToAddToNewProject;
    }

    public void setSkillIdsToAddToNewProject(List<Long> skillIdsToAddToNewProject) {
        this.skillIdsToAddToNewProject = skillIdsToAddToNewProject;
    }

    public List<Long> getSkillIdsToAddToSelectedProjectToUpdate() {
        return skillIdsToAddToSelectedProjectToUpdate;
    }

    public void setSkillIdsToAddToSelectedProjectToUpdate(List<Long> skillIdsToAddToSelectedProjectToUpdate) {
        this.skillIdsToAddToSelectedProjectToUpdate = skillIdsToAddToSelectedProjectToUpdate;
    }

    public Project getProjectDeleteSkill() {
        return projectDeleteSkill;
    }

    public void setProjectDeleteSkill(Project projectDeleteSkill) {
        this.projectDeleteSkill = projectDeleteSkill;
    }

    public Company getCompanyToDisplayProject() {
        return companyToDisplayProject;
    }

    public void setCompanyToDisplayProject(Company companyToDisplayProject) {
        this.companyToDisplayProject = companyToDisplayProject;
    }

    public Project getProjectToView() {
        return projectToView;
    }

    public void setProjectToView(Project projectToView) {
        this.projectToView = projectToView;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public Project getProjectToViewMS() {
        return projectToViewMS;
    }

    public void setProjectToViewMS(Project projectToViewMS) {
        this.projectToViewMS = projectToViewMS;
    }
    
    
    
     

 
     
}
