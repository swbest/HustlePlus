/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Application;
import entity.Project;
import entity.Student;
import java.util.ArrayList;
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
import util.exception.ApproveApplicationException;
import util.exception.DeleteApplicationException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.StudentAppliedToProjectException;
import util.exception.StudentAssignedToProjectException;
import util.exception.StudentNotFoundException;
import util.exception.StudentNotVerifiedException;
import util.exception.StudentSuspendedException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateApplicationException;

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
    public Long createApplication(Application newApplication, Long projectId, Long studentId) throws StudentAppliedToProjectException, StudentAssignedToProjectException, StudentSuspendedException, StudentNotVerifiedException, ApplicationExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException, StudentNotFoundException {
        try {
            Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
            Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
            if (student.getIsVerified() == false) {
                throw new StudentNotVerifiedException("Student is not yet verified! Please wait a few days for admin staff to verify.");
            }
            if (student.getIsSuspended() == true) {
                throw new StudentSuspendedException("Student is suspended. Please contact admin staff for details.");
            }
            if (student.getProjects().contains(project)) {
                throw new StudentAssignedToProjectException("Student is already working on this project.");
            }
            List<Application> currApplications = student.getApplications();
            for (Application application : currApplications) {
                if (application.getProject().equals(project)) {
                    throw new StudentAppliedToProjectException("Student is already applied to this project.");
                }
            }
            newApplication.setProject(project);
            newApplication.setStudent(student);
            newApplication.setIsApproved(Boolean.FALSE);
            newApplication.setIsPending(Boolean.TRUE);
            project.addApplication(newApplication);
            student.addApplication(newApplication);
            em.persist(newApplication);
            em.flush();
            return newApplication.getApplicationId();
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
    public List<Application> retrieveApplicationByStudent(Long studentId
    ) {
        Query query = em.createQuery("SELECT a FROM Application a WHERE a.student.userId =:studentId");
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Override
    public List<Application> retrieveApplicationByProject(Long projectId
    ) {
        Query query = em.createQuery("SELECT a FROM Application a WHERE a.project.projectId =:pid");
        query.setParameter("pid", projectId);
        return query.getResultList();
    }

    @Override
    public List<Student> retrieveStudentByApprovedApplication(Long projectId) throws ApplicationNotFoundException {
        try {
            Query query = em.createQuery("SELECT a FROM Application a WHERE a.project.projectId =:pid AND a.isApproved = TRUE ");
            query.setParameter("pid", projectId);

            List<Application> applicationList = query.getResultList();
            List<Student> studentList = new ArrayList();
            for (Application a : applicationList) {
                Application app = retrieveApplicationByApplicationId(a.getApplicationId());
                studentList.add(app.getStudent());
            }

            return studentList;

        } catch (ApplicationNotFoundException ex) {
            throw new ApplicationNotFoundException("Application cannot be found!");
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
        if (applicationToRemove.getIsPending() == Boolean.TRUE) {
            em.remove(applicationToRemove);
        } else {
            throw new DeleteApplicationException("Application for " + applicationToRemove.getProject().getProjectName() + " has been processed and cannot be deleted!");
        }
        em.remove(applicationToRemove);
    }

    @Override
    public List<Application> retrieveAllApplications() {
        System.out.println("retrievedApplications");
        Query query = em.createQuery("SELECT a FROM Application a");
        return query.getResultList();
    }

    @Override
    public void approveApplication(Long appId) throws ApproveApplicationException, ApplicationNotFoundException {

        try {
            Application appToApprove = retrieveApplicationByApplicationId(appId);
            if (appToApprove.getIsApproved() == false && appToApprove.getIsPending() == true) {
                appToApprove.setIsApproved(Boolean.TRUE);
                appToApprove.setIsPending(Boolean.FALSE);

                //Associate student with project 
                Student studentOfApplication = appToApprove.getStudent();
                Project projectOfApplication = appToApprove.getProject();

                List<Student> studentsInProject = projectOfApplication.getStudents();
                studentsInProject.add(studentOfApplication);
                projectOfApplication.setStudents(studentsInProject);

                List<Project> projectsOfStudent = studentOfApplication.getProjects();
                projectsOfStudent.add(projectOfApplication);
                studentOfApplication.setProjects(projectsOfStudent);

            } else if (appToApprove.getIsApproved() == true && appToApprove.getIsPending() == false) {
                throw new ApproveApplicationException("Application has been approved!");
            } else if (appToApprove.getIsApproved() == false && appToApprove.getIsPending() == false) {
                throw new ApproveApplicationException("Application has been rejected!");
            }
        } catch (ApplicationNotFoundException ex) {
            throw new ApplicationNotFoundException("Application does not exist!");
        }
    }

    @Override
    public void rejectApplication(Long appId) throws ApproveApplicationException, ApplicationNotFoundException {

        try {
            Application appToReject = retrieveApplicationByApplicationId(appId);
            if (appToReject.getIsApproved() == false && appToReject.getIsPending() == true) {
                appToReject.setIsApproved(Boolean.FALSE);
                appToReject.setIsPending(Boolean.FALSE);
            } else if (appToReject.getIsApproved() == true && appToReject.getIsPending() == false) {
                throw new ApproveApplicationException("Application has been approved!");
            } else if (appToReject.getIsApproved() == false && appToReject.getIsPending() == false) {
                throw new ApproveApplicationException("Application has been rejected!");
            }
        } catch (ApplicationNotFoundException ex) {
            throw new ApplicationNotFoundException("Application does not exist!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Application>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
