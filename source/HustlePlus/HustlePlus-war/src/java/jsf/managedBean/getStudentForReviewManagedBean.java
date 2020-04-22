/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Company;
import entity.Project;
import entity.Student;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.exception.ApplicationNotFoundException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "getStudentForReviewManagedBean")
@RequestScoped
public class getStudentForReviewManagedBean {
    
    private Student studentToReview; 
    private Project projectToReview; 
    private Company companyReviewing; 

    /**
     * Creates a new instance of getStudentForReviewManagedBean
     */
    public getStudentForReviewManagedBean() {
    }
    
    @PostConstruct
    public void PostConstruct() {
        studentToReview = (Student)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("studentToReview");
        projectToReview = (Project)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectToReview");
        companyReviewing = (Company)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyReviewing");
    }
    
        public void leaveReview(ActionEvent event) {
            try {
                 projectToReview = (Project) event.getComponent().getAttributes().get("projectToReview");
                 studentToReview = (Student) event.getComponent().getAttributes().get("studentToReview");
                 companyReviewing = (Company) event.getComponent().getAttributes().get("companyReviewing");
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectToReview", projectToReview); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("studentToReview", studentToReview); 
                ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyReviewing", companyReviewing); 
                 FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewStudentInProject.xhtml");
         } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving team: " + ex.getMessage(), null));
         } 
         }
    


    public Student getStudentToReview() {
        return studentToReview;
    }

    public void setStudentToReview(Student studentToReview) {
        this.studentToReview = studentToReview;
    }

    public Project getProjectToReview() {
        return projectToReview;
    }

    public void setProjectToReview(Project projectToReview) {
        this.projectToReview = projectToReview;
    }

    public Company getCompanyReviewing() {
        return companyReviewing;
    }

    public void setCompanyReviewing(Company companyReviewing) {
        this.companyReviewing = companyReviewing;
    }
    
    
    
    
}
