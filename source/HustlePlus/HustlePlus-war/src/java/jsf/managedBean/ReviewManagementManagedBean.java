/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ApplicationSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.StudentReviewSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.Company;
import entity.Project;
import entity.Student;
import entity.StudentReview;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.ApplicationNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;
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

    @EJB(name = "ApplicationSessionBeanLocal")
    private ApplicationSessionBeanLocal applicationSessionBeanLocal;

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;


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
    private Long studentIdToReview; 
    
    private Project selectedProject; 
    
    private Company companyLeavingReview; 
    private List<Project> projectsCompanyHas; 
    private List<Student> studentsInProjects; 
    
    private List<StudentReview> allStudentReviews;
    
    private StudentReview selectedReviewToUpdate;
    
    private StudentReview reviewToView; 
            
    

    /**
     * Creates a new instance of ReviewManagementManagedBean
     */
    public ReviewManagementManagedBean() {
        newStudentReview  = new StudentReview(); 
    }
    
    @PostConstruct
    public void postConstruct() {
        
       studentReviews = studentReviewSessionBeanLocal.retrieveAllStudentReviews(); 
        companyLeavingReview = (Company)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyLeavingReview");
        projectsCompanyHas = (List<Project>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectsCompanyHas");
        studentsInProjects = (List<Student>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("studentsInProjects");
        allStudentReviews = (List<StudentReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("allStudentReviews");
    }
    
    public void createNewReview(ActionEvent event) {
   try {
       
       System.out.println("********** createReviewForStudent: " + newStudentReview.getReviewText());
       System.out.println("PROJECTIDINRMM" + selProjectId); 
       boolean check = true; 
       
        Project p = projectSessionBeanLocal.retrieveProjectByProjectId(selProjectId);
        Company c = (Company) event.getComponent().getAttributes().get("cReviewing");
        Student s = studentSessionBeanLocal.retrieveStudentByStudentId(studentIdToReview);
        newStudentReview.setUsername(c.getName());
        
        List<Student>studentsInProject = p.getStudents();
        for(Student sp:studentsInProject ) {
            List <Project> projects = s.getProjects();
            for (Project pj: projects) {
                if (pj.equals(p)) {
                   check = true; 
                   break; 
                } else {
                    check = false; 
                }
            }
            
        }
        if (check = true) {
        Long reviewId = studentReviewSessionBeanLocal.createStudentReviewByCompany(getNewStudentReview(), s.getUserId(), p.getProjectId(), c.getUserId());
        getStudentReviews().add(studentReviewSessionBeanLocal.retrieveStudentReviewByReviewId(reviewId)); 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review successfully created!", null));
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student did not participate in selected project!", null));
        }


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
    
    public void doCreateReview(ActionEvent event) {
        try {
            companyLeavingReview = (Company) event.getComponent().getAttributes().get("companyLeavingReview");
            ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyLeavingReview", companyLeavingReview);
            projectsCompanyHas = projectSessionBeanLocal.retrieveProjectsByCompany(companyLeavingReview.getUserId());
            ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectsCompanyHas", projectsCompanyHas); 
            List<Student> s = new ArrayList();  
            for(Project p:projectsCompanyHas) {
                 List<Student> studentsOfP = applicationSessionBeanLocal.retrieveStudentByApprovedApplication(p.getProjectId());
                 for (Student stu:studentsOfP) {
                     s.add(stu); 
                 }
             }
            studentsInProjects = s ;
            ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("studentsInProjects", studentsInProjects); 
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewStudent.xhtml");
         } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating new review: " + ex.getMessage(), null));
         } catch (ProjectNotFoundException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review:  Project not found", null));
        } catch (ApplicationNotFoundException ex) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new review:  Application not found", null));
        } 
         }
    
    public void createReviewForStudent(ActionEvent event) {
   try {
       
       
       System.out.println("********** createReviewForStudent: " + newStudentReview.getReviewText());
       
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
    
    public void viewAllReviewsByCompany(ActionEvent event) {
       try {
   
        Company c = (Company) event.getComponent().getAttributes().get("companyAllReviews");
         setAllStudentReviews(studentReviewSessionBeanLocal.retrieveStudentReviewsByCompany(c.getUserId()));
         ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("allStudentReviews", allStudentReviews); 
        
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/reviewsByCompany.xhtml");
    } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while displaying reviews: " + ex.getMessage(), null));
         }
    }
    
    
    
    public void deleteReview(ActionEvent event) {
        try {
        StudentReview sr = (StudentReview) event.getComponent().getAttributes().get("reviewToDelete");
        
        allStudentReviews.remove(sr);
        
        Student stu = studentSessionBeanLocal.retrieveStudentByStudentId(sr.getStudentReviewed().getUserId());
        studentReviewSessionBeanLocal.deleteReview(sr.getStudentReviewId());
        getStudentReviews().remove(sr); 
        
        
          //To Recalculate Student's Average Rating 
          List<StudentReview> reviewsForStudent = studentReviewSessionBeanLocal.retrieveAllStudentReviewsForStudent(stu.getUserId()); 
          if (reviewsForStudent.isEmpty()) {
              stu.setAvgRating(0.0);
              studentSessionBeanLocal.updateStudent(stu);
          } else {
          Integer numReviews = reviewsForStudent.size();
          Double totalRating = 0.0 ; 
          for(StudentReview stuR: reviewsForStudent) {
                 totalRating += stuR.getRating(); 
            }
           Double calculatedRating = totalRating / numReviews; 
           stu.setAvgRating(calculatedRating);
           studentSessionBeanLocal.updateStudent(stu);
           System.out.println("STUDENT ID IN SRSB3" + stu.getUserId()); 
          }
           
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student Review deleted successfully", null));
    } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting student review: " + ex.getMessage(), null));
    }  catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the review: Student not found", null));
        }  catch (UpdateStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the review: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while deleting the review: " + ex.getMessage(), null));
        }
        
    }

    
    public void doUpdateReview(ActionEvent event) {
        selectedReviewToUpdate = (StudentReview) event.getComponent().getAttributes().get("reviewToUpdate");
 
    }
    
    public void updateReview(ActionEvent event) {
        try {
        selectedReviewToUpdate.setRating(selectedReviewToUpdate.getRating());
        selectedReviewToUpdate.setReviewText(selectedReviewToUpdate.getReviewText());
        studentReviewSessionBeanLocal.updateReview(selectedReviewToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review successfully updated!", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating student review: " + ex.getMessage(), null));
        } catch (UpdateReviewException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating student review: " + ex.getMessage(), null));
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while updating student review: " + ex.getMessage(), null));
        }  catch (UpdateStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating student review: " + ex.getMessage(), null));
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating student review: Student not found", null));
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

    public Company getCompanyLeavingReview() {
        return companyLeavingReview;
    }

    public void setCompanyLeavingReview(Company companyLeavingReview) {
        this.companyLeavingReview = companyLeavingReview;
    }

    public List<Project> getProjectsCompanyHas() {
        return projectsCompanyHas;
    }

    public void setProjectsCompanyHas(List<Project> projectsCompanyHas) {
        this.projectsCompanyHas = projectsCompanyHas;
    }

    public List<Student> getStudentsInProjects() {
        return studentsInProjects;
    }

    public void setStudentsInProjects(List<Student> studentsInProjects) {
        this.studentsInProjects = studentsInProjects;
    }

    public Long getStudentIdToReview() {
        return studentIdToReview;
    }

    public void setStudentIdToReview(Long studentIdToReview) {
        this.studentIdToReview = studentIdToReview;
    }

    public List<StudentReview> getAllStudentReviews() {
        return allStudentReviews;
    }

    public void setAllStudentReviews(List<StudentReview> allStudentReviews) {
        this.allStudentReviews = allStudentReviews;
    }

    public StudentReview getSelectedReviewToUpdate() {
        return selectedReviewToUpdate;
    }

    public void setSelectedReviewToUpdate(StudentReview selectedReviewToUpdate) {
        this.selectedReviewToUpdate = selectedReviewToUpdate;
    }

    public StudentReview getReviewToView() {
        return reviewToView;
    }

    public void setReviewToView(StudentReview reviewToView) {
        this.reviewToView = reviewToView;
    }
    
    
    
    
    
    
    
    

    
    
    
    
    

    
}
