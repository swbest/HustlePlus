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

    private Long newBookId;

    public CreateNewProjectRsp() {
    }

    public CreateNewProjectRsp(Long newBookId) {
        this.newBookId = newBookId;
    }

    public Long getNewBookId() {
        return newBookId;
    }

    public void setNewBookId(Long newBookId) {
        this.newBookId = newBookId;
    }

}
