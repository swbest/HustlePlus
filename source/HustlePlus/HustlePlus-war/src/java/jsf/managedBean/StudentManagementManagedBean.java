/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Student;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "studentManagementManagedBean")
@ViewScoped
public class StudentManagementManagedBean implements Serializable {
    

    /**
     * Creates a new instance of StudentManagementManagedBean
     */
    public StudentManagementManagedBean() {
    }
    
    
}
