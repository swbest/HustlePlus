/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.CompanyReviewSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.StudentReviewSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.Company;
import entity.Project;
import static entity.Project_.projectId;
import entity.Review;
import entity.Student;
import entity.StudentReview;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStudentException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "reviewManagementManagedBean")
@ViewScoped
public class ReviewManagementManagedBean implements Serializable {


    @EJB(name = "StudentReviewSessionBeanLocal")
    private StudentReviewSessionBeanLocal studentReviewSessionBeanLocal;

    private StudentReview newStudentReview; 
    private List<StudentReview> studentReviews; 
    
    
    private Student studentToReview;
    private Project projectToReview;
    private Company companyReviewing; 
    
    private List<Project> projects;
    private Company companyToReview;
    private Long selProjectId;
    private Project selectedProject; 
            
    

    /**
     * Creates a new instance of ReviewManagementManagedBean
     */
    public ReviewManagementManagedBean() {
        newStudentReview  = new StudentReview(); 
    }
    
    @PostConstruct
    public void postConstruct() {
        
       studentReviews = studentReviewSessionBeanLocal.retrieveAllStudentReviews(); 
       
    }
    
    public void createReviewForStudent(ActionEvent event) {
   try {
        Project p = (Project) event.getComponent().getAttributes().get("projectToReview");
        Company c = (Company) event.getComponent().getAttributes().get("companyReviewing");
        Student s = (Student) event.getComponent().getAttributes().get("studentToReview");

        System.out.println("PRINT ID" + p.getProjectId()); 
        newStudentReview.setUsername(c.getName());
        Long reviewId = studentReviewSessionBeanLocal.createStudentReviewByCompany(getNewStudentReview(), s.getUserId(), p.getProjectId(), c.getUserId());
       getStudentReviews().add(studentReviewSessionBeanLocal.retrieveStudentReviewByReviewId(reviewId)); 
       

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review successfully created!", null));
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new review: Student not found", null));
        } catch (ProjectNotFoundException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review:  Project not found", null));
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: Company not found", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: Review not found", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: " + ex.getMessage(), null));
        } catch (UpdateStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while updating the student rating: " + ex.getMessage(), null));
        }
    }

    public void onrate() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Rating : Rating successfully added!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }  
    /*public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/

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

    public Company getCompanyToReview() {
        return companyToReview;
    }

    public void setCompanyToReview(Company companyToReview) {
        this.companyToReview = companyToReview;
    }

    /**
     * @return the selProjectId
     */
    public Long getSelProjectId() {
        return selProjectId;
    }

    /**
     * @param selProjectId the selProjectId to set
     */
    public void setSelProjectId(Long selProjectId) {
        this.selProjectId = selProjectId;
    }

    /**
     * @return the selectedProject
     */
    public Project getSelectedProject() {
        return selectedProject;
    }

    /**
     * @param selectedProject the selectedProject to set
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
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

    public Company getCompanyReviewing() {
        return companyReviewing;
    }

    public void setCompanyReviewing(Company companyReviewing) {
        this.companyReviewing = companyReviewing;
    }

    public StudentReview getNewStudentReview() {
        return newStudentReview;
    }

    public void setNewStudentReview(StudentReview newStudentReview) {
        this.newStudentReview = newStudentReview;
    }

    public List<StudentReview> getStudentReviews() {
        return studentReviews;
    }

    public void setStudentReviews(List<StudentReview> studentReviews) {
        this.studentReviews = studentReviews;
    }
    
    

    
    
    
    
    

    
}
