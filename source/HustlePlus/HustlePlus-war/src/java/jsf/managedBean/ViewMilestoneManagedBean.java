/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Milestone;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author amand
 */
@Named(value = "viewMilestoneManagedBean")
@RequestScoped
public class ViewMilestoneManagedBean implements Serializable 
{
    private Milestone milestoneToView;
    
    /**
     * Creates a new instance of ViewMilestoneManagedBean
     */
    public ViewMilestoneManagedBean() {
        milestoneToView = new Milestone();
    }
    
    @PostConstruct
    public void postConstruct()
    {        
    }

    /**
     * @return the milestoneToView
     */
    public Milestone getMilestoneToView() {
        return milestoneToView;
    }

    /**
     * @param milestoneToView the milestoneToView to set
     */
    public void setMilestoneToView(Milestone milestoneToView) {
        this.milestoneToView = milestoneToView;
    }
    
    
    
}
