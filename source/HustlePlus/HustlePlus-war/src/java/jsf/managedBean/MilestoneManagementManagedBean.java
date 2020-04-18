/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.MilestoneSessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import entity.Company;
import entity.Milestone;
import entity.Payment;
import entity.Project;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
@ViewScoped
public class MilestoneManagementManagedBean implements Serializable{

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
    private Project selectedProject; 
    
    private Milestone milestoneToUpdate;
    private Long projectIdUpdate;
    private List<Long> paymentIdsUpdate;
    
    private List<Milestone> milestonesForSelectedCompany; 
    private Company selectedMilestoneCompany; 
    
    

    
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
        setProjects(projectSessionBeanLocal.retrieveAllProjects());
        
//        for (Project project:projects) {
//            selectItems.add(new SelectItem(project));
//        }
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("milestonesProject", projects); 
   
    }
    
    public void viewMilestoneDetails(javax.faces.event.ActionEvent event) throws IOException
    {
        Long milestoneIdToView = (Long)event.getComponent().getAttributes().get("milestoneId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("milestoneIdToView", milestoneIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewMilestone.xhtml");
    }
    
    public void createNewMilestone(ActionEvent event)
    {
       
       System.out.println("id" + selProjectId);
       
       for(Project p:projects)
       {
           if(p.getProjectId().equals(selProjectId))
           {
               selectedProject = p;
               break;
           }
       }

        if (selectedProject != null) {
            System.out.println("MMMB0");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected Project is " + selectedProject.getProjectName(), "Selected Project is " + selectedProject.getProjectName()));
        } else {
            System.out.println("MMMB1");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a valid project","Please select a valid project"));
        }
 
        try
        {
            System.out.println("MMMB2");
            //Project selectedProject = (Project) event.getComponent().getAttributes().get("selectedProject");
            //Long selProjectId = selectedProject.getProjectId();
            System.out.println("id" + selectedProject.getProjectId());
            Long milestoneId = milestoneSessionBeanLocal.createNewMilestone(newMilestone, selProjectId);
            System.out.println("MMMB3");
            getMilestones().add(milestoneSessionBeanLocal.retrieveMilestoneByMilestoneId(milestoneId));
            
            newMilestone = new Milestone();
            selProjectId = null;
            newPaymentIds = null;
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New milestone created successfully (Milestone ID: " + milestoneId + ")", null));
            
        } catch (MilestoneNotFoundException | MilestoneIdExistException |ProjectNotFoundException | InputDataValidationException | UnknownPersistenceException ex)
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
    
    public void updateMilestone(ActionEvent event)
    {                    
        
        
        if(selProjectId == 0)
        {
            selProjectId = null;
        }
        
        try
        {
            milestoneSessionBeanLocal.updateMilestone(milestoneToUpdate);
                        
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
    
    public void deleteMilestone(ActionEvent event)
    { 
        try
        {
            Milestone milestoneToDelete = (Milestone)event.getComponent().getAttributes().get("milestoneToDelete");
            milestoneSessionBeanLocal.deleteMilestone(milestoneToDelete.getMilestoneId());
            
            milestones.remove(milestoneToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Milestone deleted successfully", null));
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
    
    public void retrieveMilestonesForCompany() {
        System.out.println("CALLED");
        try {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/companies/milestoneManagement.xhtml");

         setMilestonesForSelectedCompany(milestoneSessionBeanLocal.retrieveMilestonesByCompany(selectedMilestoneCompany.getUserId())); 
        } catch (IOException | ProjectNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving milestones: " + ex.getMessage(), null));
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

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public List<Milestone> getMilestonesForSelectedCompany() {
        return milestonesForSelectedCompany;
    }

    public void setMilestonesForSelectedCompany(List<Milestone> milestonesForSelectedCompany) {
        this.milestonesForSelectedCompany = milestonesForSelectedCompany;
    }

    public Company getSelectedMilestoneCompany() {
        return selectedMilestoneCompany;
    }

    public void setSelectedMilestoneCompany(Company selectedMilestoneCompany) {
        this.selectedMilestoneCompany = selectedMilestoneCompany;
    }
    
    
    
    



  

    
    
}
