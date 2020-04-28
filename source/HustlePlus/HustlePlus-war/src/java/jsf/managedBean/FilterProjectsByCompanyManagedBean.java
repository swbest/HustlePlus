/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.StudentReviewSessionBeanLocal;
import entity.Company;
import entity.CompanyReview;
import entity.Project;
import entity.StudentReview;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author amanda
 */
@Named(value = "filterProjectsByCompanyManagedBean")
@ViewScoped
public class FilterProjectsByCompanyManagedBean implements Serializable {

    @EJB(name = "CompanyReviewSessionBeanLocal")
    private CompanyReviewSessionBeanLocal companyReviewSessionBeanLocal;
    
    

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    
    @EJB(name = "StudentReviewSessionBeanLocal")
    private StudentReviewSessionBeanLocal studentReviewSessionBeanLocal;
    
    @Inject
    private ViewProjectManagedBean viewProjectManagedBean;
    
    private String condition;
    private List<Long> selectedCompanyIds;
    private List<SelectItem> selectItems;
    private List<Project> projects;
    private Project projectToView; 
    private List<StudentReview> reviewsOfStudent; 
    private List<CompanyReview> reviewsOfProject; 


    /**
     * Creates a new instance of FilterProjectsByCompanyManagedBean
     */
    public FilterProjectsByCompanyManagedBean() {
        condition = "OR";
    }
    
    @PostConstruct
    public void postConstruct()
            
    {
        setReviewsOfStudent((List<StudentReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfStudent"));
        setReviewsOfProject((List<CompanyReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfProject"));
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
            projects = projectSessionBeanLocal.filterProjectByCompanies(selectedCompanyIds);
        }
        else
        {
            setProjects(projectSessionBeanLocal.retrieveAllProjects());
        }

    }
    
    public void viewProjectDetails(ActionEvent event) throws IOException
    {
        Long projectIdToView = (Long)event.getComponent().getAttributes().get("projectId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("projectIdToView", projectIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "filterProjectsByCompany");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewProject.xhtml");
    }
    
        public void retrieveReviewsForStudents(ActionEvent event) {
       try {
        Project projectToRetrieveReview = (Project) event.getComponent().getAttributes().get("projectToViewReview");
        System.out.println(projectToView.getProjectId());
         setReviewsOfStudent(studentReviewSessionBeanLocal.retrieveStudentReviewsByProject(projectToRetrieveReview.getProjectId()));
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfStudent", getReviewsOfStudent()); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewsOfStudentInOtherProjects.xhtml");
        
    } catch(IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
        
    }

    }
        
     public void retrieveReviewsForProject(ActionEvent event) {
         try {
        Project projectToRetrieveReview = (Project) event.getComponent().getAttributes().get("projectToViewReview");
        setReviewsOfProject(companyReviewSessionBeanLocal.retrieveCompanyReviewsByProject(projectToRetrieveReview.getProjectId()));
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfProject", getReviewsOfProject()); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectReviewsForOtherProjects.xhtml");
        
    } catch(IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
        
    }
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

    public Project getProjectToView() {
        return projectToView;
    }

    public void setProjectToView(Project projectToView) {
        this.projectToView = projectToView;
    }

    public List<StudentReview> getReviewsOfStudent() {
        return reviewsOfStudent;
    }

    public void setReviewsOfStudent(List<StudentReview> reviewsOfStudent) {
        this.reviewsOfStudent = reviewsOfStudent;
    }

    public List<CompanyReview> getReviewsOfProject() {
        return reviewsOfProject;
    }

    public void setReviewsOfProject(List<CompanyReview> reviewsOfProject) {
        this.reviewsOfProject = reviewsOfProject;
    }
    
    
    
    
    
}
