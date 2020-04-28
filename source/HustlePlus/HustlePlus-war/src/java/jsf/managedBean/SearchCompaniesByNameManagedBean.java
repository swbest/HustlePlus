/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Company;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import util.exception.CompanyNotFoundException;

/**
 *
 * @author amanda
 */
@Named(value = "searchCompaniesByNameManagedBean")
@RequestScoped
public class SearchCompaniesByNameManagedBean {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;
    
    @Inject
    private ViewCompanyManagedBean viewCompanyManagedBean; 
    


    private String searchString;
    private List<Company> companies;
    private Company companyToView; 

    public SearchCompaniesByNameManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setSearchString((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companySearchString"));

        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setCompanies(companySessionBeanLocal.retrieveAllCompanies());
        } else {
                setCompanies(companySessionBeanLocal.searchCompaniesByName(getSearchString()));   
        }
    }

    public void searchCompany() {
        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setCompanies(companySessionBeanLocal.retrieveAllCompanies());
        } else {
            
       setCompanies(companySessionBeanLocal.searchCompaniesByName(getSearchString()));
           
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

    /**
     * @return the companies
     */
    public List<Company> getCompanies() {
        return companies;
    }

    /**
     * @param companies the companies to set
     */
    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public ViewCompanyManagedBean getViewCompanyManagedBean() {
        return viewCompanyManagedBean;
    }

    public void setViewCompanyManagedBean(ViewCompanyManagedBean viewCompanyManagedBean) {
        this.viewCompanyManagedBean = viewCompanyManagedBean;
    }

    public Company getCompanyToView() {
        return companyToView;
    }

    public void setCompanyToView(Company companyToView) {
        this.companyToView = companyToView;
    }
    
    
    

    
}
