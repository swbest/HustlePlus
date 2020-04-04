/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Skill;

/**
 *
 * @author dtjldamien
 */
public class CreateNewSkillReq {

    private Skill newSkill;

    public CreateNewSkillReq() {
    }

    public CreateNewSkillReq(Skill newSkill) {
        this.newSkill = newSkill;
    }

    public Skill getNewSkill() {
        return newSkill;
    }

    public void setNewSkill(Skill newSkill) {
        this.newSkill = newSkill;
    }

}
