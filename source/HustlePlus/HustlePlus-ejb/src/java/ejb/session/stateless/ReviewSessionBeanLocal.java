/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
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
public interface ReviewSessionBeanLocal {

    public Review createNewReview(Review newReview) throws UnknownPersistenceException, InputDataValidationException;

    public List<Review> retrieveAllReviews();

    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateReview(Review review) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException;

    public void deleteReview(Long reviewId) throws ReviewNotFoundException;
    
}
