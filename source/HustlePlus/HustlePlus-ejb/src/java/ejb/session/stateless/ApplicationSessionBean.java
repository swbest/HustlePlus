/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Application;
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
import util.exception.ApplicationExistException;
import util.exception.ApplicationNotFoundException;
import util.exception.DeleteApplicationException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.StudentNotFoundException;
import util.exception.StudentNotVerifiedException;
import util.exception.StudentSuspendedException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author dtjldamien
 */
@Stateless
public class ApplicationSessionBean implements ApplicationSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @EJB
    private ProjectSessionBeanLocal projectSessionBeanLocal;
    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;

    public ApplicationSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Application createApplication(Application newApplication, Long projectId, Long studentId) throws StudentSuspendedException, StudentNotVerifiedException, ApplicationExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException, StudentNotFoundException {
        try {
            Set<ConstraintViolation<Application>> constraintViolations = validator.validate(newApplication);
            Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
            Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
            if (student.getIsVerified() == false) {
                throw new StudentNotVerifiedException("Student is not yet verified! Please wait a few days for admin staff to verify.");
            }
            if (student.getIsSuspended() == false) {
                throw new StudentSuspendedException("Student is suspended. Please contact admin staff for details.");
            }
            if (constraintViolations.isEmpty()) {
                newApplication.setProject(project);
                newApplication.setStudent(student);
                project.addApplication(newApplication);
                student.addApplication(newApplication);
                em.persist(newApplication);
                em.flush();
                return newApplication;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ApplicationExistException("Student name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Application retrieveApplicationByApplicationId(Long applicationId) throws ApplicationNotFoundException {
        Application application = em.find(Application.class, applicationId);

        if (application != null) {
            return application;
        } else {
            throw new ApplicationNotFoundException("Application ID " + applicationId + " does not exist!");
        }
    }

    @Override
    public void updateApplication(Application application) throws ApplicationNotFoundException, UpdateApplicationException, InputDataValidationException {
        if (application != null && application.getApplicationId() != null) {
            Set<ConstraintViolation<Application>> constraintViolations = validator.validate(application);

            if (constraintViolations.isEmpty()) {
                Application applicationToUpdate = retrieveApplicationByApplicationId(application.getApplicationId());
                applicationToUpdate.setIsApproved(application.getIsApproved());
                applicationToUpdate.setIsPending(application.getIsPending());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ApplicationNotFoundException("Student Id not provided for student to be updated");
        }
    }

    @Override
    public void deleteApplication(Long applicationId) throws ApplicationNotFoundException, DeleteApplicationException {
        Application applicationToRemove = retrieveApplicationByApplicationId(applicationId);
        if (applicationToRemove.getIsApproved() == Boolean.FALSE && applicationToRemove.getIsPending() == Boolean.FALSE) {
            em.remove(applicationToRemove);
        } else {
            throw new DeleteApplicationException("Application ID " + applicationId + " has been processed and cannot be deleted!");
        }
        em.remove(applicationToRemove);
    }

    @Override
    public List<Application> retrieveAllApplicationss() {
        Query query = em.createQuery("SELECT a FROM Application a");
        return query.getResultList();
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Application>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
