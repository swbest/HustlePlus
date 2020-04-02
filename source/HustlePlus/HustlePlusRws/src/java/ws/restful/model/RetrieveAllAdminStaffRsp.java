/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.AdminStaff;
import java.util.List;

/**
 *
 * @author sw_be
 */
public class RetrieveAllAdminStaffRsp {
    
    public List<AdminStaff> adminStaffs;

    public RetrieveAllAdminStaffRsp() {
    }

    public RetrieveAllAdminStaffRsp(List<AdminStaff> adminStaffs) {
        this.adminStaffs = adminStaffs;
    }

    public List<AdminStaff> getAdminStaffs() {
        return adminStaffs;
    }

    public void setAdminStaffs(List<AdminStaff> adminStaffs) {
        this.adminStaffs = adminStaffs;
    }

}
