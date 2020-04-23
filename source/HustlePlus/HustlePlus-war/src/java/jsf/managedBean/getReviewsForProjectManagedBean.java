/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import ejb.session.stateless.StudentReviewSessionBeanLocal;
import entity.Company;
import entity.CompanyReview;
import entity.Project;
import entity.StudentReview;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getReviewsForProjectManagedBean")
@ViewScoped
public class getReviewsForProjectManagedBean implements Serializable{

    @EJB(name = "StudentReviewSessionBeanLocal")
    private StudentReviewSessionBeanLocal studentReviewSessionBeanLocal;

    @EJB(name = "CompanyReviewSessionBeanLocal")
    private CompanyReviewSessionBeanLocal companyReviewSessionBeanLocal;

    private List<StudentReview> reviewsOfStudent; 
    private List<CompanyReview> reviewsOfProject; 
    private List<CompanyReview> reviewsOfCompany; 
    

    /**
     * Creates a new instance of getReviewsForStudentManagedBean
     */
    public getReviewsForProjectManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        System.out.println("********* getReviewsForProjectManagedBean: postConstruct");
        setReviewsOfStudent((List<StudentReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfStudent"));
        setReviewsOfProject((List<CompanyReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfProject"));
        setReviewsOfCompany((List<CompanyReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfCompany"));
    }
    
    public void retrieveReviewsForStudents(ActionEvent event) {
       try {
        Project projectToRetrieveReview = (Project) event.getComponent().getAttributes().get("projectToViewReview");
       reviewsOfStudent = studentReviewSessionBeanLocal.retrieveStudentReviewsByProject(projectToRetrieveReview.getProjectId());
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfStudent", reviewsOfStudent); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewsOfStudentInProject.xhtml");
        
    } catch(IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
        
    }

    }
    
    public void retrieveReviewsForProject(ActionEvent event) {
         try {
        Project projectToRetrieveReview = (Project) event.getComponent().getAttributes().get("projectToViewReview");
            setReviewsOfProject(companyReviewSessionBeanLocal.retrieveCompanyReviewsByProject(projectToRetrieveReview.getProjectId()));
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfProject", reviewsOfProject); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/projectReviews.xhtml");
        
    } catch(IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
        
    }
    }
    
     public void retrieveReviewsForCompany(ActionEvent event) {
         try {
        Company companyToRetrieveReview = (Company) event.getComponent().getAttributes().get("companyRev");
            setReviewsOfCompany(companyReviewSessionBeanLocal.retrieveAllCompanyReviewsForCompany(companyToRetrieveReview.getUserId()));
       ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfCompany", reviewsOfCompany); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/companyReviews.xhtml");
        
    } catch(IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
        
    }
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

    public List<CompanyReview> getReviewsOfCompany() {
        return reviewsOfCompany;
    }

    public void setReviewsOfCompany(List<CompanyReview> reviewsOfCompany) {
        this.reviewsOfCompany = reviewsOfCompany;
    }
    
    
    
    
    
    
}
