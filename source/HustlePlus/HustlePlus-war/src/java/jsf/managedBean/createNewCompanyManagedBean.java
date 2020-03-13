/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Company;
import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.CompanyNameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "createNewCompanyManagedBean")
@RequestScoped
public class createNewCompanyManagedBean {

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    private Company newCompany ; 
    
    /**
     * Creates a new instance of createNewCompanyManagedBean
     */
    public createNewCompanyManagedBean() {
        
        newCompany = new Company(); 
    }
    
    public void createNewCompany(ActionEvent event) {
        
        try {
            
        Company c = companySessionBeanLocal.createNewCompany(newCompany);
        newCompany = new Company() ; 
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Company created successfully (Company ID: " + c.getUserId() + ")", null));
     } catch (CompanyNameExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: The company name already exist", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new company: " + ex.getMessage(), null));
        }
    }


    public Company getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(Company newCompany) {
        this.newCompany = newCompany;
    }
    
    
    
}
