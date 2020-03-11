/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StudentReview;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

/**
 *
 * @author sw_be
 */
@Local
public interface StudentReviewSessionBeanLocal {

    public StudentReview createNewStudentReview(StudentReview newStudentReview) throws UnknownPersistenceException, InputDataValidationException;

    public List<StudentReview> retrieveAllStudentReviews();

    public StudentReview retrieveStudentReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateStudentReview(StudentReview studentReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException;

    public void deleteStudentReview(Long reviewId) throws ReviewNotFoundException;
    
}
