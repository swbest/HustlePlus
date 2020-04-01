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
public class CreateNewProjectRsp {

    private Long newProjectId;

    public CreateNewProjectRsp() {
    }

    public CreateNewProjectRsp(Long newProjectId) {
        this.newProjectId = newProjectId;
    }

    public Long getNewProjectId() {
        return newProjectId;
    }

    public void setNewProjectId(Long newProjectId) {
        this.newProjectId = newProjectId;
    }

}
