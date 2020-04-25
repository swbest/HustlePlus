/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.StudentReview;

/**
 *
 * @author dtjldamien
 */
public class CreateNewStudentReviewReq {

    private StudentReview newStudentReview;
    private Long projectId;
    private Long studentId;

    public CreateNewStudentReviewReq() {
    }

    public CreateNewStudentReviewReq(StudentReview newStudentReview, Long projectId, Long studentId) {
        this.newStudentReview = newStudentReview;
        this.projectId = projectId;
        this.studentId = studentId;
    }

    public StudentReview getNewStudentReview() {
        return newStudentReview;
    }

    public void setNewStudentReview(StudentReview newStudentReview) {
        this.newStudentReview = newStudentReview;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
