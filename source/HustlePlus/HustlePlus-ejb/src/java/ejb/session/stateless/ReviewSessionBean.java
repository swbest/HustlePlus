/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Project;
import entity.Review;
import entity.Student;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
@Stateless
public class ReviewSessionBean implements ReviewSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;
    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    public ReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewReview(Review newReview, Long projectId, Long studentId, Long companyId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException {
        System.out.println("PROJECTID RSB:" + projectId);
        System.out.println("STUDENTID RSB:" + studentId);
        System.out.println("COMPANYID RSB:" + companyId);
        try {
            Set<ConstraintViolation<Review>> constraintViolations = validator.validate(newReview);

            if (constraintViolations.isEmpty()) {
                try {
                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    Company company = companySessionBeanLocal.retrieveCompanyByCompanyId(companyId);
                    newReview.setProject(project);
                    newReview.setCompany(company);
                    newReview.setStudent(student);
                   // project.getReviews().add(newReview);
                    //student.getCompanyReviews().add(newReview);
                    //company.getStudentReviews().add(newReview);
                    em.persist(newReview);
                    em.flush();
                    return newReview.getReviewId();
                } catch (ProjectNotFoundException ex) {
                    throw new ProjectNotFoundException("Project Not Found for ID: " + projectId);
                } catch (StudentNotFoundException ex) {
                    throw new StudentNotFoundException("Student Not Found for ID: " + studentId);
                } catch (CompanyNotFoundException ex) {
                    throw new CompanyNotFoundException("Company Not Found for ID: " + companyId);
                }
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
    public List<Review> retrieveAllReviewsForCompany(Long companyId) {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.company.userId =:cid ");
        query.setParameter("cid", companyId);
        
        return query.getResultList(); 
                

    }
    
    @Override
    public List<Review> retrieveAllReviewsForStudent(Long studentId) {
         Query query = em.createQuery("SELECT r FROM Review r WHERE r.student.userId =:sid ");
        query.setParameter("sid", studentId);
        
        return query.getResultList();

    }
    
    
    @Override
    public List<Review> retrieveReviewsByProject(Long projectId) {
        Query query = em.createQuery("SELECT r FROM Review r WHERE r.project.projectId =:pid");
        query.setParameter("pid", projectId);
        
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
