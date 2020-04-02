/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.AdminStaffSessionBeanLocal;
import entity.AdminStaff;
import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.AdminStaffNameExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserEmailExistsException;

/**
 *
 * @author amanda
 */
@Named(value = "createNewAdminStaffManagedBean")
@RequestScoped
public class CreateNewAdminStaffManagedBean {

    @EJB(name = "AdminStaffSessionBeanLocal")
    private AdminStaffSessionBeanLocal adminStaffSessionBeanLocal;
    
    private AdminStaff newAdminStaff;

    /**
     * Creates a new instance of CreateNewAdminSTaffManagedBean
     */
    public CreateNewAdminStaffManagedBean() {
        newAdminStaff = new AdminStaff();
    }
    
    public void createNewAdminStaff(ActionEvent event)
    {
        try
        {
            Long adminId = adminStaffSessionBeanLocal.createNewAdminStaff(getNewAdminStaff());
            setNewAdminStaff(new AdminStaff());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New administrator created successfully " + adminId, null));
        
        } catch (UserEmailExistsException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new administrator: The administrator name already exists!", null));
        }  catch (InputDataValidationException | UnknownPersistenceException ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new administrator: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the newAdminStaff
     */
    public AdminStaff getNewAdminStaff() {
        return newAdminStaff;
    }

    /**
     * @param newAdminStaff the newAdminStaff to set
     */
    public void setNewAdminStaff(AdminStaff newAdminStaff) {
        this.newAdminStaff = newAdminStaff;
    }
    
}
