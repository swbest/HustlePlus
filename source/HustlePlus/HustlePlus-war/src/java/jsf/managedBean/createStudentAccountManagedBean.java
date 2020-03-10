/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.StudentSessionBeanLocal;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "createStudentAccountManagedBean")
@RequestScoped
public class createStudentAccountManagedBean {

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;

    /**
     * Creates a new instance of createStudentAccountManagedBean
     */
    public createStudentAccountManagedBean() {
    }
    
    
    
}
