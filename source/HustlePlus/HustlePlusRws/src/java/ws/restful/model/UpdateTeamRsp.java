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
public class UpdateTeamRsp {

    private Long teamId;

    public UpdateTeamRsp() {
    }

    public UpdateTeamRsp(Long newTeamId) {
        this.teamId = newTeamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
