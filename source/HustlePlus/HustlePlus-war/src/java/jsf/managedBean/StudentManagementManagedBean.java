/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.StudentSessionBeanLocal;
import entity.Student;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;
    
    
    private List<Student> students;
    private List<Student> filteredStudents; 
    
    
    

    /**
     * Creates a new instance of StudentManagementManagedBean
     */
    public StudentManagementManagedBean() {
    }
    
    @PostConstruct 
    public void postConstruct() {
        
        setStudents(studentSessionBeanLocal.retrieveAllStudents());
        
    }
    
     public void verifyStudent(ActionEvent event) {
        try {
            Student selectedStudentToVerify = (Student) event.getComponent().getAttributes().get("selectedStudentToVerify");
            studentSessionBeanLocal.verifyStudent(selectedStudentToVerify.getUserId());
        } catch (StudentNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying student account: " + ex.getMessage(), null));
        } catch (VerifyStudentException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while verifying student account: " + ex.getMessage(), null));
        }

    }

    public void suspendStudent(ActionEvent event) {
        try {
            Student selectedStudentToSuspend = (Student) event.getComponent().getAttributes().get("selectedStudentToSuspend");
            studentSessionBeanLocal.suspendStudent(selectedStudentToSuspend.getUserId());
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
    
    
    
    
}
