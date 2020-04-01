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
public class RetrieveTeamRsp {

    public Team team;

    public RetrieveTeamRsp() {
    }

    public RetrieveTeamRsp(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setProject(Team team) {
        this.team = team;
    }

}
