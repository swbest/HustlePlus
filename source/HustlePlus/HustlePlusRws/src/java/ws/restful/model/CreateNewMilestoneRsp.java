/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author sw_be
 */
public class CreateNewMilestoneRsp {
    
    private Long newMilestoneId;

    public CreateNewMilestoneRsp() {
    }

    public CreateNewMilestoneRsp(Long newMilestoneId) {
        this.newMilestoneId = newMilestoneId;
    }

    public Long getNewMilestoneId() {
        return newMilestoneId;
    }

    public void setNewMilestoneId(Long newMilestoneId) {
        this.newMilestoneId = newMilestoneId;
    }
}
