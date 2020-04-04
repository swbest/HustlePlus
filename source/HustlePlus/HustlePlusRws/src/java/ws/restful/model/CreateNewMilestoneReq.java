/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Milestone;

/**
 *
 * @author sw_be
 */
public class CreateNewMilestoneReq {
    
    private Milestone newMilestone;
    private Long projectId;

    public CreateNewMilestoneReq() {
    }

    public CreateNewMilestoneReq(Milestone newMilestone, Long projectId) {
        this.newMilestone = newMilestone;
        this.projectId = projectId;
    }

    public Milestone getNewMilestone() {
        return newMilestone;
    }

    public void setNewMilestone(Milestone newMilestone) {
        this.newMilestone = newMilestone;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
