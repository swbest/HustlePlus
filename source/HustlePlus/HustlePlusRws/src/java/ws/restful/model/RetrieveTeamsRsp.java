/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Team;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveTeamsRsp {

    public List<Team> teams;

    public RetrieveTeamsRsp() {
    }

    public RetrieveTeamsRsp(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
