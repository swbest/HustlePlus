/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StudentReview;
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
import util.exception.UpdateStudentException;

/**
 *
 * @author Nurhidayah
 */
@Local
public interface StudentReviewSessionBeanLocal {

    public Long createStudentReviewByStudent(StudentReview newReview, Long projectId, Long studentId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, UpdateStudentException, ProjectNotFoundException;

    public Long createStudentReviewByCompany(StudentReview newReview, Long studentId, Long projectId, Long companyId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException, UpdateStudentException;

    public List<StudentReview> retrieveAllStudentReviews();

    public List<StudentReview> retrieveAllStudentReviewsForStudent(Long studentId);

    public List<StudentReview> retrieveStudentReviewsByProject(Long projectId);

    public StudentReview retrieveStudentReviewByReviewId(Long reviewId) throws ReviewNotFoundException;

    public void updateStudentReview(StudentReview review) throws ReviewNotFoundException, CompanyNotFoundException, UpdateCompanyException, UpdateReviewException, InputDataValidationException, StudentNotFoundException, UpdateStudentException;

    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException;

    public List<StudentReview> retrieveStudentReviewsByCompany(Long companyId);

    public void deleteReview(Long reviewId) throws ReviewNotFoundException, StudentNotFoundException, UpdateStudentException, InputDataValidationException;

    public void updateReview(StudentReview studentReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException, UpdateStudentException, StudentNotFoundException;

    public List<StudentReview> retrieveMyReviewsFromStudents(Long studentId);

    public List<StudentReview> retrieveMyReviewsFromCompanies(Long studentId);

    
}
