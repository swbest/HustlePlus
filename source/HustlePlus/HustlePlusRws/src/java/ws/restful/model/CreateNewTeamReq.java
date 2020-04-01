/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Team;

/**
 *
 * @author dtjldamien
 */
public class CreateNewTeamReq {

    private Team newTeam;
    private Long projectId;

    public CreateNewTeamReq() {
    }

    public CreateNewTeamReq(Team newTeam) {
        this.newTeam = newTeam;
    }

    public Team getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(Team newTeam) {
        this.newTeam = newTeam;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

}
