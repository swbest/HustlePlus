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
public class RetrieveMilestoneRsp {
    
    public Milestone milestone;

    public RetrieveMilestoneRsp() {
    }

    public RetrieveMilestoneRsp(Milestone milestone) {
        this.milestone = milestone;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }
}
