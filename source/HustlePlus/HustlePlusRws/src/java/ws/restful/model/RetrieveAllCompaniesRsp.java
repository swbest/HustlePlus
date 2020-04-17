/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Company;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveAllCompaniesRsp {

    public List<Company> companies;

    public RetrieveAllCompaniesRsp() {
    }

    public RetrieveAllCompaniesRsp(List<Company> companies) {
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
