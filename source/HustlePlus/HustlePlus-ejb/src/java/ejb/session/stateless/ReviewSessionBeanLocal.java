/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

/**
 *
 * @author sw_be
 */
@Local
public interface ReviewSessionBeanLocal {

    public Long createNewReview(Review newReview, Long projectId, Long studentId, Long companyId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException;

    public List<Review> retrieveAllReviews();

    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateReview(Review review) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException;

    public void deleteReview(Long reviewId) throws ReviewNotFoundException;

    public List<Review> retrieveAllReviewsForCompany(Long companyId);

    public List<Review> retrieveAllReviewsForStudent(Long studentId);

    public List<Review> retrieveReviewsByProject(Long projectId);

    
}
