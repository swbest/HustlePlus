/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Project;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author amanda
 */
@Named(value = "viewProjectManagedBean")
@RequestScoped
public class ViewProjectManagedBean implements Serializable {
    
    private Project projectToView;

    /**
     * Creates a new instance of ViewProjectManagedBean
     */
    public ViewProjectManagedBean() {
        projectToView = new Project();
    }
    
    @PostConstruct
    public void postConstruct()
    {
    }

    /**
     * @return the projectToView
     */
    public Project getProjectToView() {
        return projectToView;
    }

    /**
     * @param projectToView the projectToView to set
     */
    public void setProjectToView(Project projectToView) {
        this.projectToView = projectToView;
    }
    
}
