/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.CompanyReview;

/**
 *
 * @author dtjldamien
 */
public class RetrieveCompanyReviewRsp {
    
    public CompanyReview companyReview;

    public RetrieveCompanyReviewRsp() {
    }

    public RetrieveCompanyReviewRsp(CompanyReview companyReview) {
        this.companyReview = companyReview;
    }

    public CompanyReview getCompanyReview() {
        return companyReview;
    }

    public void setCompanyReview(CompanyReview companyReview) {
        this.companyReview = companyReview;
    }
}
