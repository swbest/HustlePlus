/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author dtjldamien
 */
public class CreateNewSkillRsp {

    private Long newSkillId;

    public CreateNewSkillRsp() {
    }

    public CreateNewSkillRsp(Long newSkillId) {
        this.newSkillId = newSkillId;
    }

    public Long getNewSkillId() {
        return newSkillId;
    }

    public void setNewSkillId(Long newSkillId) {
        this.newSkillId = newSkillId;
    }

}
