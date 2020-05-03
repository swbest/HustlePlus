/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

/**
 *
 * @author dtjldamien
 */
public class CreateNewCompanyReviewRsp {
    
    private Long newCompanyReviewId;

    public CreateNewCompanyReviewRsp() {
    }

    public CreateNewCompanyReviewRsp(Long newCompanyReviewId) {
        this.newCompanyReviewId = newCompanyReviewId;
    }

    public Long getNewCompanyReviewId() {
        return newCompanyReviewId;
    }

    public void setNewCompanyReviewId(Long newCompanyReviewId) {
        this.newCompanyReviewId = newCompanyReviewId;
    }
}
