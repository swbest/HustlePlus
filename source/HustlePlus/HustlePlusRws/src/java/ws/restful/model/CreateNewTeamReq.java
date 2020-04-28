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
    private Long studentId;

    public CreateNewTeamReq() {
    }

    public CreateNewTeamReq(Team newTeam, Long studentId) {
        this.newTeam = newTeam;
        this.studentId = studentId;
    }

    public Team getNewTeam() {
        return newTeam;
    }

    public void setNewTeam(Team newTeam) {
        this.newTeam = newTeam;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

}
