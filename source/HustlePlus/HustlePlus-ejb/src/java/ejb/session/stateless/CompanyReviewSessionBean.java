/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.CompanyReview;
import entity.Project;
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
import util.exception.UpdateCompanyException;
import util.exception.UpdateReviewException;

/**
 *
 * @author Nurhidayah
 */
@Stateless
public class CompanyReviewSessionBean implements CompanyReviewSessionBeanLocal {

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

    public CompanyReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createCompanyReview(CompanyReview newReview, Long projectId, Long companyId, Long studentId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException {
        try {
            Set<ConstraintViolation<CompanyReview>> constraintViolations = validator.validate(newReview);

            if (constraintViolations.isEmpty()) {
                try {
                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    Company company = companySessionBeanLocal.retrieveCompanyByCompanyId(companyId);
                    Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    newReview.setProject(project);
                    newReview.setCompany(company);
                    newReview.setStudent(student);
                    project.getCompanyReviews().add(newReview);
                    student.getCompanyReviews().add(newReview);
                    company.getCompanyReviews().add(newReview);

                    em.persist(newReview);
                    em.flush();

                    //To Recalculate Company's Average Rating 
                    List<CompanyReview> reviewsForCompany = retrieveAllCompanyReviewsForCompany(companyId);
                    Integer numReviews = reviewsForCompany.size() + 1;
                    Double totalRating = 0.0;
                    for (CompanyReview cr : reviewsForCompany) {
                        totalRating += cr.getRating();
                    }
                    Double calculatedRating = (totalRating + newReview.getRating()) / numReviews;
                    company.setAvgRating(calculatedRating);
                    companySessionBeanLocal.updateCompany(company);

                    em.persist(newReview);
                    em.flush();
                    return newReview.getCompanyReviewId();
                } catch (ProjectNotFoundException ex) {
                    throw new ProjectNotFoundException("Project Not Found for ID: " + projectId);
                } catch (StudentNotFoundException ex) {
                    throw new StudentNotFoundException("Student Not Found for ID: " + studentId);
                } catch (CompanyNotFoundException ex) {
                    throw new CompanyNotFoundException("Company Not Found for ID: " + companyId);
                } catch (UpdateCompanyException ex) {
                    throw new CompanyNotFoundException("Company Not Updated: " + companyId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<CompanyReview> retrieveAllCompanyReviews() {
        Query query = em.createQuery("SELECT r FROM CompanyReview r");

        return query.getResultList();
    }

    @Override
    public List<CompanyReview> retrieveAllCompanyReviewsForCompany(Long companyId) {
        Query query = em.createQuery("SELECT r FROM CompanyReview r WHERE r.company.userId =:cid ");
        query.setParameter("cid", companyId);

        return query.getResultList();

    }

    @Override
    public List<CompanyReview> retrieveCompanyReviewsByProject(Long projectId) {
        Query query = em.createQuery("SELECT r FROM CompanyReview r WHERE r.project.projectId =:pid");
        query.setParameter("pid", projectId);

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
    public List<CompanyReview> retrieveAllCompanyReviewsForStudent(Long studentId) {
        Query query = em.createQuery("SELECT r FROM CompanyReview r WHERE r.student.userId =:sid ");
        query.setParameter("sid", studentId);

        return query.getResultList();
    }

    @Override
    public void updateCompanyReview(CompanyReview review) throws ReviewNotFoundException, CompanyNotFoundException, UpdateCompanyException, UpdateReviewException, InputDataValidationException {
        if (review != null && review.getCompanyReviewId() != null) {
            Set<ConstraintViolation<CompanyReview>> constraintViolations = validator.validate(review);

            if (constraintViolations.isEmpty()) {
                CompanyReview reviewToUpdate = retrieveCompanyReviewByReviewId(review.getCompanyReviewId());
                reviewToUpdate.setReviewText(review.getReviewText());
                reviewToUpdate.setRating(review.getRating());
                reviewToUpdate.setProject(review.getProject());
                reviewToUpdate.setCompany(review.getCompany());
                reviewToUpdate.setStudent(review.getStudent());

                //To Recalculate Company's Average Rating 
                Company company = review.getCompany();
                List<CompanyReview> reviewsForCompany = retrieveAllCompanyReviewsForCompany(company.getUserId());
                Integer numReviews = reviewsForCompany.size() + 1;
                Double totalRating = 0.0;
                for (CompanyReview cr : reviewsForCompany) {
                    totalRating += cr.getRating();
                }
                Double calculatedRating = (totalRating + review.getRating()) / numReviews;
                review.getCompany().setAvgRating(calculatedRating);
                companySessionBeanLocal.updateCompany(company);

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Review Id not provided for review to be updated");
        }
    }

    @Override
    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException {
        CompanyReview reviewToRemove = retrieveCompanyReviewByReviewId(reviewId);
        em.remove(reviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CompanyReview>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
