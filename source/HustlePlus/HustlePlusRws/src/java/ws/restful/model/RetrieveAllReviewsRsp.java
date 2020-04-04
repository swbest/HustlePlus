/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Review;
import java.util.List;

/**
 *
 * @author sw_be
 */
public class RetrieveAllReviewsRsp {
    
    public List<Review> reviews;

    public RetrieveAllReviewsRsp() {
    }

    public RetrieveAllReviewsRsp(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
