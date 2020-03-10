/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

/**
 *
 * @author sw_be
 */
@Stateless
@Local(ReviewSessionBeanLocal.class)
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewReview(Review newReview) throws UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(newReview);

            if (constraintViolations.isEmpty()) {
                em.persist(newReview);
                em.flush();

                return newReview.getReviewId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Review> retrieveAllReviews() {
        Query query = em.createQuery("SELECT r FROM Review r");

        return query.getResultList();
    }

    @Override
    public Review retrieveReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        Review review = em.find(Review.class, reviewId);

        if (review != null) {
            return review;
        } else {
            throw new ReviewNotFoundException("Review ID " + reviewId + " does not exist!");
        }
    }

    @Override
    public void updateReview(Review review) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException {
        if (review != null && review.getReviewId()!= null) {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(review);

            if (constraintViolations.isEmpty()) {
                Review reviewToUpdate = retrieveReviewByReviewId(review.getReviewId());
                reviewToUpdate.setReviewText(review.getReviewText());
                reviewToUpdate.setRating(review.getRating());
                reviewToUpdate.setProject(review.getProject());
                reviewToUpdate.setCompany(review.getCompany());
                reviewToUpdate.setStudent(review.getStudent());
                        
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Review Id not provided for review to be updated");
        }
    }

    @Override
    public void deleteReview(Long reviewId) throws ReviewNotFoundException {
        Review reviewToRemove = retrieveReviewByReviewId(reviewId);
        em.remove(reviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Review>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
