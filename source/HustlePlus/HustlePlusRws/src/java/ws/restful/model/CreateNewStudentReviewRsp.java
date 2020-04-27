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
public class CreateNewStudentReviewRsp {
    
    private Long newStudentReviewId;

    public CreateNewStudentReviewRsp() {
    }

    public CreateNewStudentReviewRsp(Long newStudentReviewId) {
        this.newStudentReviewId = newStudentReviewId;
    }

    public Long getNewStudentReviewId() {
        return newStudentReviewId;
    }

    public void setNewStudentReviewId(Long newStudentReviewId) {
        this.newStudentReviewId = newStudentReviewId;
    }
}
