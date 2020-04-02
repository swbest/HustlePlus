/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Company;

/**
 *
 * @author sw_be
 */
public class CreateNewCompanyReq {
    
    private Company newCompany;

    public CreateNewCompanyReq() {
    }

    public CreateNewCompanyReq(Company newCompany) {
        this.newCompany = newCompany;
    }

    public Company getNewCompany() {
        return newCompany;
    }

    public void setNewCompany(Company newCompany) {
        this.newCompany = newCompany;
    }
}
