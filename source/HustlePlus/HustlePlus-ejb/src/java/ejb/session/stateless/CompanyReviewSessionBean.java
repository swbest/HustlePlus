/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CompanyReview;
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
 */
@Stateless
public class CompanyReviewSessionBean implements CompanyReviewSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CompanyReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public CompanyReview createNewCompanyReview(CompanyReview newCompanyReview) throws UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<CompanyReview>> constraintViolations = validator.validate(newCompanyReview);

            if (constraintViolations.isEmpty()) {
                em.persist(newCompanyReview);
                em.flush();

                return newCompanyReview;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<CompanyReview> retrieveAllCompanyReviews() {
        Query query = em.createQuery("SELECT cr FROM CompanyReview cr");

        return query.getResultList();
    }

    @Override
    public CompanyReview retrieveCompanyReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        CompanyReview companyReview = em.find(CompanyReview.class, reviewId);

        if (companyReview != null) {
            return companyReview;
        } else {
            throw new ReviewNotFoundException("Company Review ID " + reviewId + " does not exist!");
        }
    }

    @Override
    public void updateCompanyReview(CompanyReview companyReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException {
        if (companyReview != null && companyReview.getReviewId()!= null) {
            Set<ConstraintViolation<CompanyReview>> constraintViolations = validator.validate(companyReview);

            if (constraintViolations.isEmpty()) {
                Review companyReviewToUpdate = retrieveCompanyReviewByReviewId(companyReview.getReviewId());
                companyReviewToUpdate.setReviewText(companyReview.getReviewText());
                companyReviewToUpdate.setRating(companyReview.getRating());
                companyReviewToUpdate.setProject(companyReview.getProject());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Company Review Id not provided for company review to be updated");
        }
    }

    @Override
    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException {
        Review companyReviewToRemove = retrieveCompanyReviewByReviewId(reviewId);
        em.remove(companyReviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CompanyReview>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
