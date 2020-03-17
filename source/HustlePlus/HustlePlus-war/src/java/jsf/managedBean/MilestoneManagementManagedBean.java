/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Milestone;
import entity.Project;
import java.awt.event.ActionEvent;
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
    private List<Project> projects;
    
    private Milestone milestoneToUpdate;
    private Long projectIdUpdate;

    
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
            Milestone m = milestoneSessionBeanLocal.createNewMilestone(getNewMilestone()); //project? payment?
            getMilestones().add(m);
            
            setNewMilestone(new Milestone());
        
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

    
    
}
