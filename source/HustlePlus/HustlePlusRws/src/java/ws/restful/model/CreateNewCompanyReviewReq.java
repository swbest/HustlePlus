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
public class CreateNewCompanyReviewReq {

    private CompanyReview newCompanyReview;
    private Long projectId;
    private Long companyId;
    private Long studentId;

    public CreateNewCompanyReviewReq() {
    }

    public CreateNewCompanyReviewReq(CompanyReview newCompanyReview, Long projectId, Long companyId, Long studentId) {
        this.newCompanyReview = newCompanyReview;
        this.projectId = projectId;
        this.companyId = companyId;
        this.studentId = studentId;
    }

    public CompanyReview getNewCompanyReview() {
        return newCompanyReview;
    }

    public void setNewCompanyReview(CompanyReview newCompanyReview) {
        this.newCompanyReview = newCompanyReview;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
