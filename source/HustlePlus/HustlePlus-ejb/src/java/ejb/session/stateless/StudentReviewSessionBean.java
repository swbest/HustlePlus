/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Project;
import entity.Student;
import entity.StudentReview;
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
import util.exception.UpdateStudentException;

/**
 *
 * @author Nurhidayah
 */
@Stateless
public class StudentReviewSessionBean implements StudentReviewSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @EJB
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    public StudentReviewSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createStudentReviewByStudent(StudentReview newReview, Long projectId, Long studentId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, UpdateStudentException, ProjectNotFoundException {
        try {
            Set<ConstraintViolation<StudentReview>> constraintViolations = validator.validate(newReview);

            if (constraintViolations.isEmpty()) {
                try {
                    Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    newReview.setStudentReviewed(student);
                    student.getStudentReviews().add(newReview);

                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    newReview.setProject(project);
                    project.addStudentReview(newReview);

                    em.persist(newReview);
                    em.flush();

                    //To Recalculate Student's Average Rating 
                    List<StudentReview> reviewsForStudent = retrieveAllStudentReviewsForStudent(studentId);
                    Integer numReviews = reviewsForStudent.size();
                    Double totalRating = 0.0;
                    for (StudentReview sr : reviewsForStudent) {
                        totalRating += sr.getRating();
                    }
                    Double calculatedRating = totalRating / numReviews;
                    student.setAvgRating(calculatedRating);
                    studentSessionBeanLocal.updateStudent(student);

                    em.persist(newReview);
                    em.flush();
                    return newReview.getStudentReviewId();
                } catch (StudentNotFoundException ex) {
                    throw new StudentNotFoundException("Student Not Found for ID: " + studentId);
                } catch (UpdateStudentException ex) {
                    throw new UpdateStudentException("Cannot Update Student: " + studentId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }

    @Override
    public Long createStudentReviewByCompany(StudentReview newReview, Long studentId, Long projectId, Long companyId) throws UnknownPersistenceException, InputDataValidationException, StudentNotFoundException, ProjectNotFoundException, CompanyNotFoundException, UpdateStudentException {
        try {
            Set<ConstraintViolation<StudentReview>> constraintViolations = validator.validate(newReview);

            if (constraintViolations.isEmpty()) {
                try {
                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    Company company = companySessionBeanLocal.retrieveCompanyByCompanyId(companyId);
                    Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
                    newReview.setProject(project);
                    newReview.setCompany(company);
                    newReview.setStudentReviewed(student);
                    project.getStudentReviews().add(newReview);
                    student.getStudentReviews().add(newReview);
                    company.getStudentReviews().add(newReview);

                    em.persist(newReview);
                    em.flush();

                    //To Recalculate Company's Average Rating 
                    List<StudentReview> reviewsForStudent = retrieveAllStudentReviewsForStudent(studentId);
                    Integer numReviews = reviewsForStudent.size();
                    System.out.println("sizeoFNUMREVIEWS" + numReviews);
                    Double totalRating = 0.0;
                    for (StudentReview cr : reviewsForStudent) {
                        totalRating += cr.getRating();
                    }
                    System.out.println("totalr" + totalRating);
                    Double calculatedRating = totalRating / numReviews;
                    student.setAvgRating(calculatedRating);
                    studentSessionBeanLocal.updateStudent(student);

                    return newReview.getStudentReviewId();
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
    public void deleteReview(Long reviewId) throws ReviewNotFoundException, StudentNotFoundException, UpdateStudentException, InputDataValidationException {

        try {
            StudentReview sr = retrieveStudentReviewByReviewId(reviewId);
            Student stu = studentSessionBeanLocal.retrieveStudentByStudentId(sr.getStudentReviewed().getUserId());
            System.out.println("STUDENT ID IN SRSB1" + stu.getUserId());
            em.remove(sr);
            System.out.println("STUDENT ID IN SRSB2" + stu.getUserId());

        } catch (StudentNotFoundException ex) {
            throw new StudentNotFoundException("Student Not Found");
        }
    }

    @Override
    public void updateReview(StudentReview studentReview) throws ReviewNotFoundException, UpdateReviewException, InputDataValidationException, UpdateStudentException, StudentNotFoundException {
        if (studentReview != null && studentReview.getStudentReviewId() != null) {
            Set<ConstraintViolation<StudentReview>> constraintViolations = validator.validate(studentReview);

            if (constraintViolations.isEmpty()) {

                try {
                    StudentReview studentReviewToUpdate = retrieveStudentReviewByReviewId(studentReview.getStudentReviewId());
                    studentReviewToUpdate.setRating(studentReview.getRating());
                    studentReviewToUpdate.setReviewText(studentReview.getReviewText());

                    //To Recalculate Student's Average Rating 
                    List<StudentReview> reviewsForStudent = retrieveAllStudentReviewsForStudent(studentReview.getStudentReviewed().getUserId());
                    Integer numReviews = reviewsForStudent.size();
                    Double totalRating = 0.0;
                    for (StudentReview sr : reviewsForStudent) {
                        totalRating += sr.getRating();
                    }
                    Double calculatedRating = totalRating / numReviews;
                    studentReview.getStudentReviewed().setAvgRating(calculatedRating);
                    studentSessionBeanLocal.updateStudent(studentReview.getStudentReviewed());
                } catch (StudentNotFoundException ex) {
                    throw new StudentNotFoundException("Student Not Found for ID: " + studentReview.getStudentReviewed().getUserId());
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Review Id not provided for review to be updated");
        }
    }

    @Override
    public List<StudentReview> retrieveAllStudentReviews() {
        Query query = em.createQuery("SELECT r FROM StudentReview r");

        return query.getResultList();
    }

    @Override
    public List<StudentReview> retrieveAllStudentReviewsForStudent(Long studentId) {
        Query query = em.createQuery("SELECT r FROM StudentReview r WHERE r.studentReviewed.userId =:sid ");
        query.setParameter("sid", studentId);

        return query.getResultList();
    }

    @Override
    public List<StudentReview> retrieveStudentReviewsByProject(Long projectId) {
        Query query = em.createQuery("SELECT r FROM StudentReview r WHERE r.project.projectId =:pid");
        query.setParameter("pid", projectId);

        return query.getResultList();
    }

    @Override
    public List<StudentReview> retrieveStudentReviewsByCompany(Long companyId) {
        Query query = em.createQuery("SELECT r FROM StudentReview r WHERE r.company.userId =:cid");
        query.setParameter("cid", companyId);
        return query.getResultList();
    }

    @Override
    public List<StudentReview> retrieveMyReviewsFromStudents(Long studentId) {
        Query query = em.createQuery("SELECT r FROM StudentReview r WHERE r.studentReviewed.userId =:sid AND r.company IS NULL");
        query.setParameter("sid", studentId);

        return query.getResultList();
    }

    @Override
    public List<StudentReview> retrieveMyReviewsFromCompanies(Long studentId) {
        Query query = em.createQuery("SELECT r FROM StudentReview r WHERE r.studentReviewed.userId =:sid AND r.company IS NOT NULL");
        query.setParameter("sid", studentId);

        return query.getResultList();
    }

    @Override
    public StudentReview retrieveStudentReviewByReviewId(Long reviewId) throws ReviewNotFoundException {
        StudentReview studentReview = em.find(StudentReview.class, reviewId);

        if (studentReview != null) {
            return studentReview;
        } else {
            throw new ReviewNotFoundException("Student Review ID " + reviewId + " does not exist!");
        }
    }

    @Override
    public void updateStudentReview(StudentReview review) throws ReviewNotFoundException, CompanyNotFoundException, UpdateCompanyException, UpdateReviewException, InputDataValidationException, StudentNotFoundException, UpdateStudentException {
        if (review != null && review.getStudentReviewId() != null) {
            Set<ConstraintViolation<StudentReview>> constraintViolations = validator.validate(review);

            if (constraintViolations.isEmpty()) {
                StudentReview reviewToUpdate = retrieveStudentReviewByReviewId(review.getStudentReviewId());
                reviewToUpdate.setReviewText(review.getReviewText());
                reviewToUpdate.setRating(review.getRating());
                reviewToUpdate.setProject(review.getProject());
                reviewToUpdate.setCompany(review.getCompany());
                reviewToUpdate.setStudentReviewed(review.getStudentReviewed());

                //To Recalculate Company's Average Rating 
                Student student = review.getStudentReviewed();
                List<StudentReview> reviewsForStudent = retrieveAllStudentReviewsForStudent(student.getUserId());
                Integer numReviews = reviewsForStudent.size();
                Double totalRating = 0.0;
                for (StudentReview sr : reviewsForStudent) {
                    totalRating += sr.getRating();
                }
                Double calculatedRating = totalRating / numReviews;
                review.getCompany().setAvgRating(calculatedRating);
                studentSessionBeanLocal.updateStudent(student);

            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ReviewNotFoundException("Review Id not provided for review to be updated");
        }

    }

    @Override
    public void deleteCompanyReview(Long reviewId) throws ReviewNotFoundException {
        StudentReview reviewToRemove = retrieveStudentReviewByReviewId(reviewId);
        em.remove(reviewToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<StudentReview>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

}
