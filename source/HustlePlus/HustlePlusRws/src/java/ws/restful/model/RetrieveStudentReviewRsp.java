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
public class RetrieveStudentReviewRsp {
    
    public StudentReview studentReview;

    public RetrieveStudentReviewRsp() {
    }

    public RetrieveStudentReviewRsp(StudentReview studentReview) {
        this.studentReview = studentReview;
    }

    public StudentReview getStudentReview() {
        return studentReview;
    }

    public void setStudentReview(StudentReview studentReview) {
        this.studentReview = studentReview;
    }
}
