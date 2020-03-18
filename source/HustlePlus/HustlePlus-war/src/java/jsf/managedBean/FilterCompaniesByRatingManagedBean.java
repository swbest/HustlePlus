/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import entity.Company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author amanda
 */
@Named(value = "filterCompaniesByRatingManagedBean")
@Dependent
public class FilterCompaniesByRatingManagedBean {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;
    
    @Inject
    private ViewCompanyManagedBean viewCompanyManagedBean;
    
    private String condition;
    private List<Long> selectedCompanyIds;
    private List<SelectItem> selectItems;
    private List<Company> companies;

    /**
     * Creates a new instance of FilterCompaniesByRatingManagedBean
     */
    public FilterCompaniesByRatingManagedBean() {
        condition = "OR";
    }
    
    @PostConstruct
    public void postConstruct()
    {

        List<Company> cs = companySessionBeanLocal.retrieveAllCompanies();
        setSelectItems(new ArrayList<>());
        
        for(Company c:cs)
        {
            getSelectItems().add(new SelectItem(c.getUserId(), c.getAvgRating().toString(), c.getAvgRating().toString()));
        }
        
        
        setCondition((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyFilterCondition"));        
        setSelectedCompanyIds((List<Long>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyFilterReviews"));
        
        filterCompany();
    }
    
    @PreDestroy
    public void preDestroy()
    {
        
    }
    
    public void filterCompany()
    {        

        if(getSelectedCompanyIds() != null && getSelectedCompanyIds().size() > 0)
        {
            //companies = companySessionBeanLocal.retrieveCompaniesByAvgRating(selectedReviewIds, condition);
        }
        else
        {
            setCompanies(companySessionBeanLocal.retrieveAllCompanies());
        }

    }
    
    public void viewCompanyDetails(ActionEvent event) throws IOException
    {
        Long companyIdToView = (Long)event.getComponent().getAttributes().get("companyId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("companyIdToView", companyIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterCompaniesByRating");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewCompany.xhtml");
    }

    /**
     * @return the viewCompanyManagedBean
     */
    public ViewCompanyManagedBean getViewCompanyManagedBean() {
        return viewCompanyManagedBean;
    }

    /**
     * @param viewCompanyManagedBean the viewCompanyManagedBean to set
     */
    public void setViewCompanyManagedBean(ViewCompanyManagedBean viewCompanyManagedBean) {
        this.viewCompanyManagedBean = viewCompanyManagedBean;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
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

    /**
     * @return the selectedCompanyIds
     */
    public List<Long> getSelectedCompanyIds() {
        return selectedCompanyIds;
    }

    /**
     * @param selectedCompanyIds the selectedCompanyIds to set
     */
    public void setSelectedCompanyIds(List<Long> selectedCompanyIds) {
        this.selectedCompanyIds = selectedCompanyIds;
    }
    
}
