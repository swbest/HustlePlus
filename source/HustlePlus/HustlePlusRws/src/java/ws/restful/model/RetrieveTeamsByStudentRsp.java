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
public class RetrieveTeamsByStudentRsp {

    public List<Team> teams;

    public RetrieveTeamsByStudentRsp() {
    }

    public RetrieveTeamsByStudentRsp(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setStudents(List<Team> teams) {
        this.teams = teams;
    }
}
