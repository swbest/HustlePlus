/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author sw_be
 */
public class CreateNewAdminStaffRsp {
    
    private Long newAdminStaffId;

    public CreateNewAdminStaffRsp() {
    }

    public CreateNewAdminStaffRsp(Long newAdminStaffId) {
        this.newAdminStaffId = newAdminStaffId;
    }

    public Long getNewAdminStaffId() {
        return newAdminStaffId;
    }

    public void setNewCompanyId(Long newAdminStaffId) {
        this.newAdminStaffId = newAdminStaffId;
    }
}
