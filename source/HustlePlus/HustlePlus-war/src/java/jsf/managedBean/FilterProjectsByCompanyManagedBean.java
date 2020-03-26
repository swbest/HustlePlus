/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Company;
import entity.Project;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author amanda
 */
@Named(value = "filterProjectsByCompanyManagedBean")
@RequestScoped
public class FilterProjectsByCompanyManagedBean {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    @Inject
    private ViewProjectManagedBean viewProjectManagedBean;
    
    private String condition;
    private List<Long> selectedCompanyIds;
    private List<SelectItem> selectItems;
    private List<Project> projects;

    /**
     * Creates a new instance of FilterProjectsByCompanyManagedBean
     */
    public FilterProjectsByCompanyManagedBean() {
        condition = "OR";
    }
    
    @PostConstruct
    public void postConstruct()
    {
        List<Company> companies = companySessionBeanLocal.retrieveAllCompanies();
        setSelectItems(new ArrayList<>());
        
        for (Company c:companies)
        {
            getSelectItems().add(new SelectItem(c.getUserId(), c.getName(), c.getName()));
        }
        
        setCondition((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projectFilterCondition"));
        setSelectedCompanyIds((List<Long>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("projectFilterCompanies"));
        
        filterProject();
    }
    
    @PreDestroy
    public void preDestroy()
    {
        
    }
    
    public void filterProject()
    {        

        if(getSelectedCompanyIds()!= null && getSelectedCompanyIds().size() > 0)
        {
            //projects = projectSessionBeanLocal.retrieveProjectsByCompany(selectedCompanyIds, condition);
        }
        else
        {
            setProjects(projectSessionBeanLocal.retrieveAllProject());
        }

    }
    
    public void viewProjectDetails(ActionEvent event) throws IOException
    {
        Long projectIdToView = (Long)event.getComponent().getAttributes().get("projectId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectIdToView", projectIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterProjectsByCompany");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProject.xhtml");
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
}
