/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Project;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "searchProjectsByCompanyManagedBean")
@RequestScoped
public class SearchProjectsByCompanyManagedBean {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    /**
     * Creates a new instance of SearchProjectsByCompanyManagedBean
     */
    public SearchProjectsByCompanyManagedBean() {
    }
    
  
    @Inject
    private ViewProjectManagedBean viewProjectManagedBean; 
   

    private String searchString;
    private List<Project> projects;
    private Project projectToView; 


    @PostConstruct
    public void postConstruct() {
        setSearchString((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companySearchString"));

        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setProjects(projectSessionBeanLocal.retrieveAllProjects());
        } else {
                setProjects(projectSessionBeanLocal.searchProjectsByCompany(searchString)); 
        }
    }

    public void searchProject() {
        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setProjects(projectSessionBeanLocal.retrieveAllProjects());
        } else {
            setProjects(projectSessionBeanLocal.searchProjectsByCompany(searchString)); 
           
        }
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

    public Project getProjectToView() {
        return projectToView;
    }

    public void setProjectToView(Project projectToView) {
        this.projectToView = projectToView;
    }
    
    
    
    
}

  
