/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.AdminStaff;

/**
 *
 * @author sw_be
 */
public class CreateNewAdminStaffReq {
    
    private AdminStaff newAdminStaff;

    public CreateNewAdminStaffReq() {
    }

    public CreateNewAdminStaffReq(AdminStaff newAdminStaff) {
        this.newAdminStaff = newAdminStaff;
    }

    public AdminStaff getNewAdminStaff() {
        return newAdminStaff;
    }

    public void setNewAdminStaff(AdminStaff newAdminStaff) {
        this.newAdminStaff = newAdminStaff;
    }
}
