/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Company;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author amanda
 */
@Named(value = "viewCompanyManagedBean")
@ViewScoped
public class ViewCompanyManagedBean implements Serializable {

    private Company companyToView;
    
    /**
     * Creates a new instance of ViewCompanyManagedBean
     */
    public ViewCompanyManagedBean() {
        companyToView = new Company();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        /*  Company company = (Company) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("companyToView");
      
      try {
        setProjectsToView(projectSessionBeanLocal.retrieveProjectsByCompany(company.getUserId()));

    } catch (ProjectNotFoundException ex)  {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
    }*/
        
    }
    
       /* public void viewProjectByCompany(ActionEvent event) {
        
        try {  
        System.out.println("VPBC1");
        Company company = (Company) event.getComponent().getAttributes().get("companyProjects");
        System.out.println(company.getUserId());
        setProjectsOfCompany(projectSessionBeanLocal.retrieveProjectsByCompany(company.getUserId()));
        if (projectsOfCompany.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("notnull");
            }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectManagement.xhtml");
                System.out.println("VPBC2");
                        if (projectsOfCompany.isEmpty()) {
            System.out.println("null");
        } else {
            System.out.println("notnull");
            }
        

        }  
    catch (ProjectNotFoundException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the project: Project not found", null));
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    } */

    public void reviews() {
       try {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviews.xhtml");
    } catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while retrieving the projects: " + ex.getMessage(), null));
    }
    }
    
    /**
     * @return the companyToView
     */
    public Company getCompanyToView() {
        return companyToView;
    }

    /**
     * @param companyToView the companyToView to set
     */
    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }
    
}
