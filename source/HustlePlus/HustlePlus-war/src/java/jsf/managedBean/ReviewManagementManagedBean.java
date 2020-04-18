/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Company;
import entity.Project;
import static entity.Project_.projectId;
import entity.Review;
import entity.Student;
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
import org.primefaces.event.RateEvent;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "reviewManagementManagedBean")
@ViewScoped
public class ReviewManagementManagedBean implements Serializable {

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;
    
    private Review newReview; 
    private List<Review> reviews; 
    private Student studentToReview;
    private Project projectToReview;
    private Company companyToReview;
            
    

    /**
     * Creates a new instance of ReviewManagementManagedBean
     */
    public ReviewManagementManagedBean() {
        newReview = new Review() ;  
    }
    
    @PostConstruct
    public void postConstruct() {
        
        setReviews(reviewSessionBeanLocal.retrieveAllReviews());
       
       
    }
    
    public void createReview(ActionEvent event) {
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewStudent.xhtml");
            studentToReview = (Student) event.getComponent().getAttributes().get("studentToReview");
            projectToReview = (Project) event.getComponent().getAttributes().get("projectToReview");
            companyToReview = (Company) event.getComponent().getAttributes().get("company");
            //int i = 1;
            //long l = i;
            Long reviewId = reviewSessionBeanLocal.createNewReview(getNewReview(), projectToReview.getProjectId() , studentToReview.getUserId(), companyToReview.getUserId());
            System.out.println("RMM1");
            getReviews().add(reviewSessionBeanLocal.retrieveReviewByReviewId(reviewId)); 
            System.out.println("RMM2");
            setNewReview(new Review());
            setStudentToReview(null); 
            setProjectToReview(null); 
            setCompanyToReview(null); 
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New project created successfully! (Project ID: " + projectId + ")", null));
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new project: Project not found", null));
        } catch (ProjectNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company has not been verified", null));
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company has been suspended", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: Company has been suspended", null));
        } catch (IOException | InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new project: " + ex.getMessage(), null));
        }
    }
    
    public void onrate() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Rating", "Rating successfully added!");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    /*public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }*/

    public Review getNewReview() {
        return newReview;
    }

    public void setNewReview(Review newReview) {
        this.newReview = newReview;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    public Company getCompanyToReview() {
        return companyToReview;
    }

    public void setCompanyToReview(Company companyToReview) {
        this.companyToReview = companyToReview;
    }

    
    
    
    
    

    
}
