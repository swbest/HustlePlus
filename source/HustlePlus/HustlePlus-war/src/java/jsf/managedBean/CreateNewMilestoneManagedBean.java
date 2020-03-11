/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import entity.Milestone;
import java.awt.event.ActionEvent;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.exception.InputDataValidationException;
import util.exception.MilestoneIdExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "createNewMilestoneManagedBean")
@RequestScoped
public class CreateNewMilestoneManagedBean {

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;
    private Milestone newMilestone; 

    /**
     * Creates a new instance of CreateNewMilestoneManagedBean
     */
    public CreateNewMilestoneManagedBean() {
        
     newMilestone = new Milestone(); 
    }
    
    
    public void createNewMilestone(ActionEvent event) {
        
        try {
            Milestone ms = milestoneSessionBeanLocal.createNewMilestone(newMilestone);
            newMilestone = new Milestone();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New milestone created successfully (Milestone ID: " + ms.getMilestoneId() + ")", null));
            
        } catch (MilestoneIdExistException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new milestone: The milestone id already exist", null));
        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has ocurred while creating the new milestone: " + ex.getMessage(), null));
        }
    }
    
    public Milestone getNewMilestone() {
        return newMilestone;  
    }
    
    public void setNewMilestone(Milestone newMilestone) {
        this.newMilestone = newMilestone ;
    }
     
}
