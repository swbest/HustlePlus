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
 * @author dtjldamien
 */
public class RetrieveMilestonesRsp {

    public List<Milestone> milestones;

    public RetrieveMilestonesRsp() {
    }

    public RetrieveMilestonesRsp(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }
}
