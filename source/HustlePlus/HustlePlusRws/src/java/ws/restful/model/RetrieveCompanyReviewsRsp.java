/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Company;
import entity.CompanyReview;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveCompanyReviewsRsp {

    public List<CompanyReview> companyReviews;

    public RetrieveCompanyReviewsRsp() {
    }

    public RetrieveCompanyReviewsRsp(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }

    public List<CompanyReview> getCompanyReviews() {
        return companyReviews;
    }

    public void setCompanyReviews(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }
}
