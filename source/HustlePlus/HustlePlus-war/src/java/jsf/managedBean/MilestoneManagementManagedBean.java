/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Milestone;
import entity.Payment;
import entity.Project;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import util.exception.InputDataValidationException;
import util.exception.MilestoneIdExistException;
import util.exception.MilestoneNotFoundException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author amanda
 */
@Named(value = "milestoneManagementManagedBean")
@RequestScoped
public class MilestoneManagementManagedBean {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @EJB(name = "MilestoneSessionBeanLocal")
    private MilestoneSessionBeanLocal milestoneSessionBeanLocal;
    
    @Inject
    private ViewMilestoneManagedBean viewMilestoneManagedBean;
    
    private List<Milestone> milestones;
    
    private Milestone newMilestone;
    private Long selProjectId;
    private List<Long> newPaymentIds;
    private List<Project> projects;
    private List<Payment> payments;
    
    private Milestone milestoneToUpdate;
    private Long projectIdUpdate;
    private List<Long> paymentIdsUpdate;

    
    /**
     * Creates a new instance of MilestoneManagementManagedBean
     */
    public MilestoneManagementManagedBean() {
        newMilestone = new Milestone();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setMilestones(milestoneSessionBeanLocal.retrieveAllMilestones());
        setProjects(projectSessionBeanLocal.retrieveAllProject());
    }
    
    public void viewMilestoneDetails(javax.faces.event.ActionEvent event) throws IOException
    {
        Long milestoneIdToView = (Long)event.getComponent().getAttributes().get("milestoneId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("milestoneIdToView", milestoneIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewMilestone.xhtml");
    }
    
    public void createNewMilestone(ActionEvent event)
    {
        
        /*
        if(selProjectId == 0)
        {
            selProjectId = null;
        }
        */
        
        try
        {
            Milestone m = milestoneSessionBeanLocal.createNewMilestone(newMilestone, selProjectId); //project? payment?
            getMilestones().add(m);
            
            newMilestone = new Milestone();
            selProjectId = null;
            newPaymentIds = null;
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New milestone created successfully (Milestone ID: " + m.getMilestoneId() + ")", null));
            
        } catch (MilestoneIdExistException |ProjectNotFoundException | InputDataValidationException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new milestone: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateMilestone(ActionEvent event)
    {
        milestoneToUpdate = (Milestone)event.getComponent().getAttributes().get("milestoneToUpdate");
        
        projectIdUpdate = milestoneToUpdate.getProject().getProjectId();
        paymentIdsUpdate = new ArrayList<>();
        
        for(Payment payment:milestoneToUpdate.getPayments())
        {
            paymentIdsUpdate.add(payment.getPaymentId());
        }

    }
    
    public void updateProduct(javax.faces.event.ActionEvent event)
    {                    
        
        /*
        if(selProjectId == 0)
        {
            selProjectId = null;
        }
        */
        
        try
        {
            milestoneSessionBeanLocal.updateMilestone(milestoneToUpdate); //project? payment?
                        
            for(Project p: projects)
            {
                if(p.getProjectId().equals(projectIdUpdate))
                {
                    milestoneToUpdate.setProject(p);
                    break;
                }                
            }
            
            milestoneToUpdate.getPayments().clear();
            
            for(Payment pm:payments)
            {
                if(paymentIdsUpdate.contains(pm.getPaymentId()))
                {
                    milestoneToUpdate.getPayments().add(pm);
                }                
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Milestone updated successfully!", null));
        }
        catch(MilestoneNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating this milestone: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void deleteProduct(ActionEvent event)
    { 
        try
        {
            Milestone milestoneToDelete = (Milestone)event.getComponent().getAttributes().get("milestoneToDelete");
            milestoneSessionBeanLocal.deleteMilestone(milestoneToDelete.getMilestoneId());
            
            milestones.remove(milestoneToDelete);
            
        }
        catch(MilestoneNotFoundException ex) //DeleteMilestoneException?
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting milestone: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }

    /**
     * @return the viewMilestoneManagedBean
     */
    public ViewMilestoneManagedBean getViewMilestoneManagedBean() {
        return viewMilestoneManagedBean;
    }

    /**
     * @param viewMilestoneManagedBean the viewMilestoneManagedBean to set
     */
    public void setViewMilestoneManagedBean(ViewMilestoneManagedBean viewMilestoneManagedBean) {
        this.viewMilestoneManagedBean = viewMilestoneManagedBean;
    }

    /**
     * @return the milestones
     */
    public List<Milestone> getMilestones() {
        return milestones;
    }

    /**
     * @param milestones the milestones to set
     */
    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    /**
     * @return the newMilestone
     */
    public Milestone getNewMilestone() {
        return newMilestone;
    }

    /**
     * @param newMilestone the newMilestone to set
     */
    public void setNewMilestone(Milestone newMilestone) {
        this.newMilestone = newMilestone;
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

    /**
     * @return the milestoneToUpdate
     */
    public Milestone getMilestoneToUpdate() {
        return milestoneToUpdate;
    }

    /**
     * @param milestoneToUpdate the milestoneToUpdate to set
     */
    public void setMilestoneToUpdate(Milestone milestoneToUpdate) {
        this.milestoneToUpdate = milestoneToUpdate;
    }

    /**
     * @return the projectIdUpdate
     */
    public Long getProjectIdUpdate() {
        return projectIdUpdate;
    }

    /**
     * @param projectIdUpdate the projectIdUpdate to set
     */
    public void setProjectIdUpdate(Long projectIdUpdate) {
        this.projectIdUpdate = projectIdUpdate;
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
     * @return the newPaymentIds
     */
    public List<Long> getNewPaymentIds() {
        return newPaymentIds;
    }

    /**
     * @param newPaymentIds the newPaymentIds to set
     */
    public void setNewPaymentIds(List<Long> newPaymentIds) {
        this.newPaymentIds = newPaymentIds;
    }

    /**
     * @return the paymentIdsUpdate
     */
    public List<Long> getPaymentIdsUpdate() {
        return paymentIdsUpdate;
    }

    /**
     * @param paymentIdsUpdate the paymentIdsUpdate to set
     */
    public void setPaymentIdsUpdate(List<Long> paymentIdsUpdate) {
        this.paymentIdsUpdate = paymentIdsUpdate;
    }

    /**
     * @return the payments
     */
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * @param payments the payments to set
     */
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }



  

    
    
}
