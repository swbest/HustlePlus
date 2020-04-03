/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Milestone;
import java.util.List;

/**
 *
 * @author sw_be
 */
public class RetrieveAllMilestonesRsp {
    
    public List<Milestone> milestones;

    public RetrieveAllMilestonesRsp() {
    }

    public RetrieveAllMilestonesRsp(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }
}
