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
public class CreateNewReviewRsp {
    
    private Long newReviewId;

    public CreateNewReviewRsp() {
    }

    public CreateNewReviewRsp(Long newReviewId) {
        this.newReviewId = newReviewId;
    }

    public Long getNewReviewId() {
        return newReviewId;
    }

    public void setNewReviewId(Long newReviewId) {
        this.newReviewId = newReviewId;
    }
}
