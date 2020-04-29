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
public class UpdateTeamReq {

    private Long teamId;
    private Long studentId;

    public UpdateTeamReq() {
    }

    public UpdateTeamReq(Long teamId, Long studentId) {
        this.teamId = teamId;
        this.studentId = studentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
