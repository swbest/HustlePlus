/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package ejb.session.stateless;

import entity.Review;
import java.util.List;
import java.util.Set;
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
 
@Stateless
public class StudentReviewSessionBean implements StudentReviewSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StudentReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Review createNewStudentReview(Review newStudentReview) throws UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(newStudentReview);

            if (constraintViolations.isEmpty()) {
                em.persist(newStudentReview);
                em.flush();

                return newStudentReview;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Review> retrieveAllStudentReviews() {
        Query query = em.createQuery("SELECT r FROM Review r");

        return query.getResultList();
    }

    @Override
    public Review retrieveStudentReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        Review studentReview = em.find(Review.class, reviewId);

        if (studentReview != null) {
            return studentReview;
        } else {
            throw new ReviewNotFoundException("Student Review ID " + reviewId + " does not exist!");
        }
    }

    @Override
    public void updateStudentReview(Review studentReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException {
        if (studentReview != null && studentReview.getReviewId()!= null) {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(studentReview);

            if (constraintViolations.isEmpty()) {
                Review studentReviewToUpdate = retrieveStudentReviewByReviewId(studentReview.getReviewId());
                studentReviewToUpdate.setReviewText(studentReview.getReviewText());
                studentReviewToUpdate.setRating(studentReview.getRating());
                studentReviewToUpdate.setProject(studentReview.getProject());
                        
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Student Review Id not provided for student review to be updated");
        }
    }

    @Override
    public void deleteStudentReview(Long reviewId) throws ReviewNotFoundException {
        Review studentReviewToRemove = retrieveStudentReviewByReviewId(reviewId);
        em.remove(studentReviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Review>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
*/