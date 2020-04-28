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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.StudentNotFoundException;

/**
 *
 * @author amanda
 */
@Named(value = "searchStudentsByNameManagedBean")
@ViewScoped
public class SearchStudentsByNameManagedBean implements Serializable{

    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;

    @Inject
    private ViewStudentManagedBean viewStudentManagedBean;

    private String searchString;
    private List<Student> students;
    private Student studentToView; 

    /**
     * Creates a new instance of SearchStudentsManagedBean
     */
    public SearchStudentsByNameManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setSearchString((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("studentSearchString"));

        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setStudents(studentSessionBeanLocal.retrieveAllStudents());
        } else {
            try {
                setStudents(studentSessionBeanLocal.retrieveStudentsByName(getSearchString()));
            } catch (StudentNotFoundException ex) {
                
            }
        }
    }

    public void searchStudent() {
        if (getSearchString() == null || getSearchString().trim().length() == 0) {
            setStudents(studentSessionBeanLocal.retrieveAllStudents());
        } else {
                setStudents(studentSessionBeanLocal.searchStudentsByName(getSearchString()));
        }
    }

    /**
     * @return the viewStudentManagedBean
     */
    public ViewStudentManagedBean getViewStudentManagedBean() {
        return viewStudentManagedBean;
    }

    /**
     * @param viewStudentManagedBean the viewStudentManagedBean to set
     */
    public void setViewStudentManagedBean(ViewStudentManagedBean viewStudentManagedBean) {
        this.viewStudentManagedBean = viewStudentManagedBean;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students the students to set
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student getStudentToView() {
        return studentToView;
    }

    public void setStudentToView(Student studentToView) {
        this.studentToView = studentToView;
    }
    
    

}
