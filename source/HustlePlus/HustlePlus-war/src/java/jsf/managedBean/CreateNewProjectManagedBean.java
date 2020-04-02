/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Milestone;
import entity.Project;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "createNewProjectManagedBean")
@RequestScoped
public class CreateNewProjectManagedBean {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    private Project newProject ; 
    private List<Project>projects; 
    private List<Milestone> milestones ; 
    private Long companyId ; 
    
    

    /**
     * Creates a new instance of CreateNewProjectManagedBean
     */
    public CreateNewProjectManagedBean() {
        
        newProject = new Project() ; 

        
    }
    
    @PostConstruct 
    public void postConstruct() {
        
        setProjects(projectSessionBeanLocal.retrieveAllProjects());
        
        
        
        
    }
    
        public void createNewProject(ActionEvent event) {
        
        try {
            Project pj = projectSessionBeanLocal.createNewProject(newProject, companyId);
            getProjects().add(pj); 
            newProject = new Project();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New project created successfully (Milestone ID: " + pj.getProjectId() + ")", null));
            
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
    
    public Project getNewProject() {
        return newProject;  
    }
    
    public void setNewProject(Project newProject) {
        this.newProject = newProject ;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    
    
}
