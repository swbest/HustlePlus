/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CompanyReview;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.ReviewNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;
import util.exception.UpdateReviewException;

/**
 *
 * @author Nurhidayah
 */
@Local
public interface CompanyReviewSessionBeanLocal {

    public Long createCompanyReview(CompanyReview newReview, Long studentId, Long projectId, Long companyId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException;

    public List<CompanyReview> retrieveAllCompanyReviews();

    public List<CompanyReview> retrieveAllCompanyReviewsForCompany(Long companyId);

    public List<CompanyReview> retrieveCompanyReviewsByProject(Long projectId);

    public CompanyReview retrieveCompanyReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateCompanyReview(CompanyReview review) throws ReviewNotFoundException, CompanyNotFoundException, UpdateCompanyException, UpdateReviewException, InputDataValidationException;

    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException;

    public List<CompanyReview> retrieveAllCompanyReviewsForStudent(Long studentId);
    
}
