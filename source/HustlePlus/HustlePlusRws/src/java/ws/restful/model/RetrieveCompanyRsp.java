/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Company;

/**
 *
 * @author dtjldamien
 */
public class RetrieveCompanyRsp {

    public Company company;

    public RetrieveCompanyRsp() {
    }

    public RetrieveCompanyRsp(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
