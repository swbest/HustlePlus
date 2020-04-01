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
public class CreateNewStudentRsp {

    private Long newStudentId;

    public CreateNewStudentRsp() {
    }

    public CreateNewStudentRsp(Long newStudentId) {
        this.newStudentId = newStudentId;
    }

    public Long getNewStudentId() {
        return newStudentId;
    }

    public void setNewStudentId(Long newStudentId) {
        this.newStudentId = newStudentId;
    }

}
