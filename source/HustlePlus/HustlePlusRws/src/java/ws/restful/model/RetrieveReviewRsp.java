/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Review;

/**
 *
 * @author sw_be
 */
public class RetrieveReviewRsp {
    
    public Review review;

    public RetrieveReviewRsp() {
    }

    public RetrieveReviewRsp(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
