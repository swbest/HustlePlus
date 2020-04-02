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
public class CreateNewCompanyRsp {
    
    private Long newCompanyId;

    public CreateNewCompanyRsp() {
    }

    public CreateNewCompanyRsp(Long newCompanyId) {
        this.newCompanyId = newCompanyId;
    }

    public Long getNewCompanyId() {
        return newCompanyId;
    }

    public void setNewCompanyId(Long newCompanyId) {
        this.newCompanyId = newCompanyId;
    }
}
