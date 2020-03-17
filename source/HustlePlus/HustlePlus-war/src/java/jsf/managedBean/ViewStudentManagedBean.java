/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Student;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author amanda
 */
@Named(value = "viewStudentManagedBean")
@RequestScoped
public class ViewStudentManagedBean implements Serializable 
{
    private Student studentToView;

    /**
     * Creates a new instance of ViewStudentManagedBean
     */
    public ViewStudentManagedBean() {
        studentToView = new Student();
    }
    
    @PostConstruct
    public void postConstruct()
    {
    }
    
    public Student getStudentToView()
    {
        return studentToView;
    }
    
    public void setStudentToView(Student studentToView)
    {
        this.studentToView = studentToView;
    }
    
}
