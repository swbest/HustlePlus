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
public class CreateNewReviewReq {
    
    private Review newReview;
    private Long projectId;
    private Long studentId;
    private Long companyId;

    public CreateNewReviewReq() {
    }

    public CreateNewReviewReq(Review newReview, Long projectId, Long studentId, Long companyId) {
        this.newReview = newReview;
        this.projectId = projectId;
        this.studentId = studentId;
        this.companyId = companyId;
    }

    public Review getNewReview() {
        return newReview;
    }

    public void setNewReview(Review newReview) {
        this.newReview = newReview;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
