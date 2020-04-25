/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.StudentReview;
import java.util.List;

/**
 *
 * @author dtjldamien
 */
public class RetrieveAllStudentReviewsRsp {
    
    public List<StudentReview> studentReviews;

    public RetrieveAllStudentReviewsRsp() {
    }

    public RetrieveAllStudentReviewsRsp(List<StudentReview> studentReviews) {
        this.studentReviews = studentReviews;
    }

    public List<StudentReview> getStudentReviews() {
        return this.studentReviews;
    }

    public void setStudentReviews(List<StudentReview> studentReviews) {
        this.studentReviews = studentReviews;
    }
}
