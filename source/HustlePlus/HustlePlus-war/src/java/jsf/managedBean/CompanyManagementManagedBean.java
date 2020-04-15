/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Company;
import entity.Project;
import entity.Review;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.SuspendCompanyException;
import util.exception.UnknownPersistenceException;
import util.exception.VerifyCompanyException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "companyManagementManagedBean")
@ViewScoped

public class CompanyManagementManagedBean implements Serializable {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;
    
    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    @Inject
    private ViewCompanyManagedBean viewCompanyManagedBean;
    
    private List<Company> companies;
    private List<Company> filteredCompanies; 
    
    private Company newCompany;
    private List<Project> projects;
    private List<Review> reviews; 
    
    private Company selectedCompanyToUpdate;
    
    private Company selectedCompanyToVerify; 
    
    private Company selectedCompanyToSuspend; 
    
    private Company selectedCompanyToEmail; 


    /**
     * Creates a new instance of companyMangementManagedBean
     */
    public CompanyManagementManagedBean() {
        
        newCompany = new Company(); 
    }
    
    @PostConstruct
    public void postConstruct() {
        setCompanies(companySessionBeanLocal.retrieveAllCompanies());   
        setProjects(projectSessionBeanLocal.retrieveAllProjects());
        setReviews(reviewSessionBeanLocal.retrieveAllReviews()); 
    }
    
    
   public void createNewCompany(ActionEvent event) {
        
        try {
            
        System.out.println("createNew1");
        Long companyId = companySessionBeanLocal.createNewCompany(newCompany);
        System.out.println("createNew2");
        companies.add(companySessionBeanLocal.retrieveCompanyByCompanyId(companyId)); 
        
        if(getFilteredCompanies() != null)
        {
                getFilteredCompanies().add(companySessionBeanLocal.retrieveCompanyByCompanyId(companyId));
        }
        
       newCompany = new Company() ; 
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Company created successfully (Company ID: " + companyId + ")", null));
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/emailVerification.xhtml");
        
        } catch (CompanyNotFoundException ex) {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: The company is not found", null));  
        } catch (CompanyNameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: The company name already exist", null));
        } catch ( InputDataValidationException | UnknownPersistenceException | IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: " + ex.getMessage(), null));
        }
    }
   
   public void doUpdateCompany(ActionEvent event) {
         selectedCompanyToUpdate =(Company)event.getComponent().getAttributes().get("selectedCompanyToUpdate");

         }
  
   
    public void updateCompany(ActionEvent event)
    {

        try
        {
            companySessionBeanLocal.updateCompany(getSelectedCompanyToUpdate());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company updated successfully", null));
        }
        catch(CompanyNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating company: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
}
    
     public void deleteCompany(ActionEvent event)
    {
        try
        {
            Company companyToDelete = (Company) event.getComponent().getAttributes().get("companyToDelete");
            companySessionBeanLocal.deleteCompany(companyToDelete.getUserId());
            companies.remove(companyToDelete); 
            
          if(getFilteredCompanies() != null)
        {
                getFilteredCompanies().remove(companyToDelete);
        }    

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Company Account deleted successfully", null));
        }
        catch(CompanyNotFoundException | DeleteCompanyException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting company account: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
     
     public void verifyCompany(ActionEvent event) {
         try {
         Company selectedCompanyToVerify = (Company)event.getComponent().getAttributes().get("selectedCompanyToVerify") ;
         companySessionBeanLocal.verifyCompany(selectedCompanyToVerify.getUserId());
         } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying company account: " + ex.getMessage(), null));
         } catch (VerifyCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying company account: " + ex.getMessage(), null));
         }

     }
     
     public void suspendCompany(ActionEvent event) {
         try {
         Company selectedCompanyToSuspend = (Company)event.getComponent().getAttributes().get("selectedCompanyToSuspend") ;
         companySessionBeanLocal.suspendCompany(selectedCompanyToSuspend.getUserId());
         } catch (CompanyNotFoundException | SuspendCompanyException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while suspending company account: " + ex.getMessage(), null));
         }

     }
     
     
     
     public void checkIfVerified(ActionEvent event) {
        
         try { 
         Company companyToCheck = (Company)event.getComponent().getAttributes().get("checkCompany") ;
         System.out.println(companyToCheck.getUserId());
        
         if (companySessionBeanLocal.checkCompany(companyToCheck)) {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/createNewProject.xhtml");
         } else {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/verificationError.xhtml");
         }
     }  catch (IOException ex) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
     }
     }

     
     public void resendEmail(ActionEvent event) {
         
             Company selectedCompanyToEmail = (Company)event.getComponent().getAttributes().get("selectedCompanyToEmail") ; 
             //companySessionBeanLocal.resendEmail(selectedCompanyToEmail.getUserId());
         
     }
     
     public ViewCompanyManagedBean getViewCompanyManagedBean() {
         return viewCompanyManagedBean; 
     }
     
     public void setViewCompanyManagedBean(ViewCompanyManagedBean vierwCompanyManagedBean) {
         this.viewCompanyManagedBean = viewCompanyManagedBean; 
     }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(Company newCompany) {
        this.newCompany = newCompany;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Company getSelectedCompanyToUpdate() {
        return selectedCompanyToUpdate;
    }

    public void setSelectedCompanyToUpdate(Company selectedCompanyToUpdate) {
        this.selectedCompanyToUpdate = selectedCompanyToUpdate;
    }

    public List<Company> getFilteredCompanies() {
        return filteredCompanies;
    }

    public void setFilteredCompanies(List<Company> filteredCompanies) {
        this.filteredCompanies = filteredCompanies;
    }

    public Company getSelectedCompanyToVerify() {
        return selectedCompanyToVerify;
    }

    public void setSelectedCompanyToVerify(Company selectedCompanyToVerify) {
        this.selectedCompanyToVerify = selectedCompanyToVerify;
    } 

    public Company getSelectedCompanyToSuspend() {
        return selectedCompanyToSuspend;
    }

    public void setSelectedCompanyToSuspend(Company selectedCompanyToSuspend) {
        this.selectedCompanyToSuspend = selectedCompanyToSuspend;
    }
    
    

}