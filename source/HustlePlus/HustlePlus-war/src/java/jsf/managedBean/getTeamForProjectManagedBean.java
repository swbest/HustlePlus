/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import entity.Project;
import entity.Student;
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
import util.exception.ApplicationNotFoundException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getTeamForProjectManagedBean")
@ViewScoped
public class getTeamForProjectManagedBean implements Serializable{

    @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;

    private List<Student> studentsForProject;
    private Project projectToDisplayTeam; 

    /**
     * Creates a new instance of getTeamForProjectManagedBean
     */
    public getTeamForProjectManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        studentsForProject = (List<Student>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("studentsForProject");
        projectToDisplayTeam = (Project)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectToDisplayTeam");
    }
            
            
    public void retrieveTeamForProject(ActionEvent event) {
            try {
                 projectToDisplayTeam = (Project) event.getComponent().getAttributes().get("projectToViewTeam");
                 studentsForProject = applicationSessionBeanLocal.retrieveStudentByApprovedApplication(projectToDisplayTeam.getProjectId()); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectToDisplayTeam", projectToDisplayTeam); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("studentsForProject", studentsForProject); 
                 FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/viewTeam.xhtml");
         } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving team: " + ex.getMessage(), null));
         } catch (ApplicationNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving team: " + ex.getMessage(), null));
         }
         }
    
    public List<Student> getStudentsForProject() {
        return studentsForProject;
    }

    public void setStudentsForProject(List<Student> studentsForProject) {
        this.studentsForProject = studentsForProject;
    }

    public Project getProjectToDisplayTeam() {
        return projectToDisplayTeam;
    }

    public void setProjectToDisplayTeam(Project projectToDisplayTeam) {
        this.projectToDisplayTeam = projectToDisplayTeam;
    }
    
    
    
}
