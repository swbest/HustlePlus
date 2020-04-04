/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Skill;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveAllSkillsRsp {

    public List<Skill> skills;

    public RetrieveAllSkillsRsp() {
    }

    public RetrieveAllSkillsRsp(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setProjects(List<Skill> skills) {
        this.skills = skills;
    }
}
