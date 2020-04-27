/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.CompanyReview;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveAllCompanyReviewsRsp {

    public List<CompanyReview> companyReviews;

    public RetrieveAllCompanyReviewsRsp() {
    }

    public RetrieveAllCompanyReviewsRsp(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }

    public List<CompanyReview> getCompanyReviews() {
        return this.companyReviews;
    }

    public void setCompanyReviews(List<CompanyReview> companyReviews) {
        this.companyReviews = companyReviews;
    }
}
