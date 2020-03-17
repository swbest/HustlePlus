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
import java.awt.event.ActionEvent;
import java.io.IOException;
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
    private Project selProject;
    private Payment newPayment;
    private Milestone newMilestone;
    private List<Project> projects;
    
    private Milestone milestoneToUpdate;
    private Long projectIdUpdate;
    private Payment paymentToUpdate;

    
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
        //FacesContext.getCurrentInstance().getExternalContext().redirect("viewMilestoneDetails.xhtml");
    }
    
    public void createNewMilestone(ActionEvent event)
    {
        
        try
        {
            Milestone m = milestoneSessionBeanLocal.createNewMilestone(newMilestone); //project? payment?
            getMilestones().add(m);
            
            newMilestone = new Milestone();
            selProject = null;
            newPayment = null;
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New milestone created successfully (Product ID: " + m.getMilestoneId() + ")", null));
            
        } catch (MilestoneIdExistException | InputDataValidationException | UnknownPersistenceException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new milestone: " + ex.getMessage(), null));
        }
    }
    
    public void doUpdateMilestone(javax.faces.event.ActionEvent event)
    {
        milestoneToUpdate = (Milestone)event.getComponent().getAttributes().get("milestoneToUpdate");
        
        setProjectIdUpdate(milestoneToUpdate.getProject().getProjectId());
        //setPaymentToUpdate(paymentToUpdate.getPayment().getPaymentId());

    }
    
    public void updateProduct(javax.faces.event.ActionEvent event)
    {                    
        
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
            
            //milestoneToUpdate.getPayment().clear();
            
            /*
            if (milestoneToUpdate.getPayment().getPaymentId().equals(paymentToUpdate.getPaymentId()))
            {
                milestoneToUpdate.setPayment(paymentToUpdate);
            }
            */

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
    
    public void deleteProduct(javax.faces.event.ActionEvent event)
    {
        
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
     * @return the selProject
     */
    public Project getSelProject() {
        return selProject;
    }

    /**
     * @param selProject the selProject to set
     */
    public void setSelProject(Project selProject) {
        this.selProject = selProject;
    }

    /**
     * @return the newPayment
     */
    public Payment getNewPayment() {
        return newPayment;
    }

    /**
     * @param newPayment the newPayment to set
     */
    public void setNewPayment(Payment newPayment) {
        this.newPayment = newPayment;
    }

    /**
     * @return the paymentToUpdate
     */
    public Payment getPaymentToUpdate() {
        return paymentToUpdate;
    }

    /**
     * @param paymentToUpdate the paymentToUpdate to set
     */
    public void setPaymentToUpdate(Payment paymentToUpdate) {
        this.paymentToUpdate = paymentToUpdate;
    }

  

    
    
}
