/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CompanyReview;
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
public interface CompanyReviewSessionBeanLocal {

    public CompanyReview createNewCompanyReview(CompanyReview newCompanyReview) throws UnknownPersistenceException, InputDataValidationException;

    public List<CompanyReview> retrieveAllCompanyReviews();

    public CompanyReview retrieveCompanyReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateCompanyReview(CompanyReview companyReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException;

    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException;
    
}
