/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.SkillSessionBeanLocal;
import entity.Project;
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
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.SkillNameExistsException;
import util.exception.SkillNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSkillException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "skillManagementManagedBean")
@ViewScoped
public class SkillManagementManagedBean implements Serializable {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;
    
    
    

    private List<Skill> skills;
    private Skill newSkill; 
    private Project projectSkillToDelete;

    /**
     * Creates a new instance of SkillManagementManagedBean
     */
    public SkillManagementManagedBean() {
        newSkill = new Skill(); 
    }
    
    
    @PostConstruct 
    public void postConstruct() {
        setSkills(skillSessionBeanLocal.retrieveAllSkills());
    }
    
    public void createNewSkill(ActionEvent event) {
         try {
             Long skillId = skillSessionBeanLocal.createNewSkill(getNewSkill()); 
             //getSkills().add(skillSessionBeanLocal.retrieveSkillBySkillId(skillId));
             newSkill = new Skill() ; 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New skill created successfully (Skill ID: " + skillId + ")", null));
             
       // } catch (SkillNotFoundException ex) {
        //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new skill: Skill not found", null));
         } catch (SkillNameExistsException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new skill: Skill name exists", null));
         } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new skill: " + ex.getMessage(), null));
         }
     }
    
    public void deleteSkillFromProject(ActionEvent event) {
        try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/skillsOfProject.xhtml");
        System.out.println(projectSkillToDelete.getProjectId()); 

        } catch (IOException ex) {
            
        }
        
    }
    /*
    public void removeSkillFromProject(ActionEvent event) {
            
        try {
            Skill skillToDelete = (Skill)event.getComponent().getAttributes().get("skillToDelete");
            Project projectToDelete = (Project)event.getComponent().getAttributes().get("projectToRemoveFromSkill");
            List<Project> projects = skillToDelete.getProjects();
            projects.remove(projectToDelete);
            skillToDelete.setProjects(projects);
            skillSessionBeanLocal.updateSkill(skillToDelete);
            List <Skill> skills = projectToDelete.getSkills();
            skills.remove(skillToDelete);
            List<Long> skillsOfProject = new ArrayList(); 
            
    } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while removing the skill: " + ex.getMessage(), null));
        } catch (SkillNotFoundException | UpdateSkillException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while removing the skill: " + ex.getMessage(), null));
        }
    }*/
    

        
        public void deleteSkill(ActionEvent event) { 
        try
        {
            Skill skillToDelete = (Skill)event.getComponent().getAttributes().get("skillToDelete");
            skillSessionBeanLocal.deleteSkill(skillToDelete.getSkillId());
            
            skills.remove(skillToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Milestone deleted successfully", null));
        }
        catch(SkillNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting milestone: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
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

    public Project getProjectSkillToDelete() {
        return projectSkillToDelete;
    }

    public void setProjectSkillToDelete(Project projectSkillToDelete) {
        this.projectSkillToDelete = projectSkillToDelete;
    }
    
    
    
    
    
}
