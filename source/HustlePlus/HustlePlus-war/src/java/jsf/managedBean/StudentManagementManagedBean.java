/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.StudentReviewSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
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
import javax.servlet.http.HttpSession;
import util.exception.StudentNotFoundException;
import util.exception.SuspendStudentException;
import util.exception.VerifyStudentException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "studentManagementManagedBean")
@ViewScoped
public class StudentManagementManagedBean implements Serializable {

    @EJB(name = "StudentReviewSessionBeanLocal")
    private StudentReviewSessionBeanLocal studentReviewSessionBeanLocal;
   

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;
    
    
    private List<Student> students;
    private List<Student> filteredStudents; 
    private Student studentToView; 
    private List<StudentReview> reviewsOfStudent; 
    
    
    

    /**
     * Creates a new instance of StudentManagementManagedBean
     */
    public StudentManagementManagedBean() {
    }
    
    @PostConstruct 
    public void postConstruct() {
        
        setStudents(studentSessionBeanLocal.retrieveAllStudents());
        setReviewsOfStudent((List<StudentReview>)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("reviewsOfStudent"));
    }
    
    public void retrieveAllReviews(ActionEvent event) {
        
        try {
        Student student = (Student) event.getComponent().getAttributes().get("selectedStudent");
        reviewsOfStudent = studentReviewSessionBeanLocal.retrieveAllStudentReviewsForStudent(student.getUserId());
        ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("reviewsOfStudent", reviewsOfStudent); 
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/companies/allReviewsOfStudent.xhtml");
    } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving reviews: " + ex.getMessage(), null));
    }
    }
    
     public void verifyStudent(ActionEvent event) {
        try {
            Student selectedStudentToVerify = (Student) event.getComponent().getAttributes().get("selectedStudentToVerify");
            if (selectedStudentToVerify.getIsVerified() == false) {
            studentSessionBeanLocal.verifyStudent(selectedStudentToVerify.getUserId());
            selectedStudentToVerify.setIsVerified(Boolean.TRUE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student successfully verified!", null));
            } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student has been verified!", null));
            }
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying student account: " + ex.getMessage(), null));
        } catch (VerifyStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying student account: " + ex.getMessage(), null));
        }

    }

    public void suspendStudent(ActionEvent event) {
        try {
            Student selectedStudentToSuspend = (Student) event.getComponent().getAttributes().get("selectedStudentToSuspend");
            if (selectedStudentToSuspend.getIsSuspended() == false) {
            studentSessionBeanLocal.suspendStudent(selectedStudentToSuspend.getUserId());
            selectedStudentToSuspend.setIsSuspended(Boolean.TRUE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Student successfully suspended!", null));
            } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Student has been suspended!", null));
            }
        } catch (StudentNotFoundException | SuspendStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while suspending student account: " + ex.getMessage(), null));
        }
    }
    
    

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getFilteredStudents() {
        return filteredStudents;
    }

    public void setFilteredStudents(List<Student> filteredStudents) {
        this.filteredStudents = filteredStudents;
    }

    public Student getStudentToView() {
        return studentToView;
    }

    public void setStudentToView(Student studentToView) {
        this.studentToView = studentToView;
    }

    public List<StudentReview> getReviewsOfStudent() {
        return reviewsOfStudent;
    }

    public void setReviewsOfStudent(List<StudentReview> reviewsOfStudent) {
        this.reviewsOfStudent = reviewsOfStudent;
    }
    
    
    
    
    
}
