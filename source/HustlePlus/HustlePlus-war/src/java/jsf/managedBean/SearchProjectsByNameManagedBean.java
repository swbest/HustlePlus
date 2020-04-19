/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Company;
import entity.Project;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import util.exception.ProjectNotFoundException;

/**
 *
 * @author amanda
 */
@Named(value = "searchProjectsByNameManagedBean")
@RequestScoped
public class SearchProjectsByNameManagedBean {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    @Inject
    private ViewProjectManagedBean viewProjectManagedBean;
    
    private String searchString;
    private List<Project> projects;
    private List<Project> projectsOfCompany; 
    private Company companyToSearchProject;

    /**
     * Creates a new instance of SearchProjectsByNameManagedBean
     */
    public SearchProjectsByNameManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setSearchString((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projectSearchString"));

        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setProjects(projectSessionBeanLocal.retrieveAllProjects());
        } else {
      
                setProjects(projectSessionBeanLocal.searchProjectsByName(getSearchString()));
           
        }
    }
    
    public void searchProject() {
        
      //  try { 
        //Long companyId = companyToSearchProject.getUserId(); 
        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            //setProjects(projectSessionBeanLocal. retrieveProjectsByCompany(companyId));
            setProjects(projectSessionBeanLocal.retrieveAllProjects()); 
        } else {
             setProjects(projectSessionBeanLocal.searchProjectsByName(getSearchString()));
            }
       // } catch (ProjectNotFoundException ex) {
         //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new project: Project not found", null));
        //}
    }
    

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * @return the viewProjectManagedBean
     */
    public ViewProjectManagedBean getViewProjectManagedBean() {
        return viewProjectManagedBean;
    }

    /**
     * @param viewProjectManagedBean the viewProjectManagedBean to set
     */
    public void setViewProjectManagedBean(ViewProjectManagedBean viewProjectManagedBean) {
        this.viewProjectManagedBean = viewProjectManagedBean;
    }

    public List<Project> getProjectsOfCompany() {
        return projectsOfCompany;
    }

    public void setProjectsOfCompany(List<Project> projectsOfCompany) {
        this.projectsOfCompany = projectsOfCompany;
    }

    public Company getCompanyToSearchProject() {
        return companyToSearchProject;
    }

    public void setCompanyToSearchProject(Company companyToSearchProject) {
        this.companyToSearchProject = companyToSearchProject;
    }
    
    
    
    
}
