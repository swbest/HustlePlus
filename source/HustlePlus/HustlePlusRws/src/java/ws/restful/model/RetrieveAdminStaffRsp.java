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
public class RetrieveAdminStaffRsp {
    
    public AdminStaff adminStaff;

    public RetrieveAdminStaffRsp() {
    }

    public RetrieveAdminStaffRsp(AdminStaff adminStaff) {
        this.adminStaff = adminStaff;
    }

    public AdminStaff getAdminStaff() {
        return adminStaff;
    }

    public void setAdminStaff(AdminStaff adminStaff) {
        this.adminStaff = adminStaff;
    }

}
