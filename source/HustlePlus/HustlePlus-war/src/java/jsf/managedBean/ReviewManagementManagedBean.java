/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
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

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;
    
    
    
    private Review newReview; 
    private List<Review> reviews; 
    
    
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
        newReview = new Review() ;  
    }
    
    @PostConstruct
    public void postConstruct() {
        
        setReviews(reviewSessionBeanLocal.retrieveAllReviews());
       
       
    }
    
    public void createReviewForStudent(ActionEvent event) {
   try {
        Project p = (Project) event.getComponent().getAttributes().get("projectToReview");
        Company c = (Company) event.getComponent().getAttributes().get("companyReviewing");
        Student s = (Student) event.getComponent().getAttributes().get("studentToReview");

        System.out.println("PRINT ID" + p.getProjectId()); 
      Long reviewId = reviewSessionBeanLocal.createNewReview(getNewReview(), p.getProjectId() , s.getUserId(), c.getUserId());
       getReviews().add(reviewSessionBeanLocal.retrieveReviewByReviewId(reviewId)); 
       
       List <Review> reviewsForStudent = reviewSessionBeanLocal.retrieveAllReviewsForStudent(s.getUserId());
       Integer numReviews = reviewsForStudent.size() + 1; 
       System.out.println(numReviews);
       Double calculatedRating = (s.getAvgRating() + newReview.getRating()) / numReviews ;
       System.out.println(calculatedRating); 
       s.setAvgRating(calculatedRating);
       studentSessionBeanLocal.updateStudent(s);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review successfully created!", null));
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new review: Project not found", null));
        } catch (ProjectNotFoundException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: Company has not been verified", null));
        } catch (CompanyNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: Company has been suspended", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: Company has been suspended", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review: " + ex.getMessage(), null));
        } catch (UpdateStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while updating the student rating: " + ex.getMessage(), null));
        }
    }
    
    public void createReview(ActionEvent event) {
        
       System.out.println("id" + selProjectId);
       
       for(Project p:projects)
       {
           if(p.getProjectId().equals(selProjectId))
           {
               selectedProject = p;
               break;
           }
       }

        if (selectedProject != null) {
            System.out.println("RMMB0");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Project is " + selectedProject.getProjectName(), "Selected Project is " + selectedProject.getProjectName()));
        } else {
            System.out.println("RMMB1");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a valid project","Please select a valid project"));
        }
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewStudent.xhtml");
            studentToReview = (Student) event.getComponent().getAttributes().get("studentToReview");
            projectToReview = (Project) event.getComponent().getAttributes().get("projectToReview");
            companyToReview = (Company) event.getComponent().getAttributes().get("company");
            //int i = 1;
            //long l = i;
            Long reviewId = reviewSessionBeanLocal.createNewReview(getNewReview(), projectToReview.getProjectId() , studentToReview.getUserId(), companyToReview.getUserId());
            System.out.println("RMM2");
            getReviews().add(reviewSessionBeanLocal.retrieveReviewByReviewId(reviewId)); 
            System.out.println("RMM3");
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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Rating : Rating successfully added!", null);
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
    
    

    
    
    
    
    

    
}
