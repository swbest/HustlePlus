/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.DeleteStudentException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.StudentNameExistException;
import util.exception.StudentNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStudentException;
import util.security.CryptographicHelper;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class StudentSessionBean implements StudentSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StudentSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Student createStudentAccount(Student newStudent) throws StudentNameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Student>> constraintViolations = validator.validate(newStudent);

            if (constraintViolations.isEmpty()) {
                em.persist(newStudent);
                em.flush();
                return newStudent;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new StudentNameExistException("Student name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Student retrieveStudentByStudentId(Long studentId) throws StudentNotFoundException {
        Student student = em.find(Student.class, studentId);

        if (student != null) {
            return student;
        } else {
            throw new StudentNotFoundException("Student ID " + studentId + " does not exist!");
        }
    }

    @Override
    public void updateStudent(Student student) throws StudentNotFoundException, UpdateStudentException, InputDataValidationException {
        if (student != null && student.getUserId() != null) {
            Set<ConstraintViolation<Student>> constraintViolations = validator.validate(student);

            if (constraintViolations.isEmpty()) {
                Student studentToUpdate = retrieveStudentByStudentId(student.getUserId());
                studentToUpdate.setUsername(student.getUsername());
                studentToUpdate.setPassword(student.getPassword());
                studentToUpdate.setIcon(student.getIcon());
                studentToUpdate.setEmail(student.getEmail());
                studentToUpdate.setName(student.getName());
                studentToUpdate.setResume(student.getResume());
                studentToUpdate.setDescription(student.getDescription());
                studentToUpdate.setAvgRating(student.getAvgRating());
                studentToUpdate.setIsVerified(student.getIsVerified());
                studentToUpdate.setIsSuspended(student.getIsSuspended());
                studentToUpdate.setSkills(student.getSkills());
                studentToUpdate.setTeams(student.getTeams());
                studentToUpdate.setCompanyReviews(student.getCompanyReviews());
                studentToUpdate.setPayments(student.getPayments());
                studentToUpdate.setBankAccountName(student.getBankAccountName());
                studentToUpdate.setBankAccountNumber(student.getBankAccountNumber());
                studentToUpdate.setApplications(student.getApplications());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new StudentNotFoundException("Student Id not provided for student to be updated");
        }
    }

    @Override
    public void deleteStudentAccount(Long studentId) throws StudentNotFoundException, DeleteStudentException {
        Student studentToRemove = retrieveStudentByStudentId(studentId);
        if (studentToRemove.getTeams().size() == 0 && studentToRemove.getCompanyReviews().size() == 0) {
            em.remove(studentToRemove);
        } else {
            throw new DeleteStudentException("Student ID " + studentId + " is associated with existing teams and reviews and cannot be deleted!");
        }
        em.remove(studentToRemove);
    }

    @Override
    public List<Student> retrieveAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");
        return query.getResultList();
    }

    @Override
    public Student retrieveStudentByUsername(String username) throws StudentNotFoundException {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Student) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StudentNotFoundException("Student Username " + username + " does not exist!");
        }
    }

    @Override
    public List retrieveStudentsByName(String name) throws StudentNotFoundException {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.name LIKE '%inStudentName%'");
        query.setParameter("inStudentName", name);
        return query.getResultList();
    }

    @Override
    public List<Student> retrieveStudentsBySkills(String skillTitle) {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.skills.title = :inSkillTitle");
        query.setParameter("inSkillTitle", skillTitle);
        return query.getResultList();
    }

    @Override
    public List<Student> retrieveStudentsByAvgRating(Double avgRating) {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.avgRating = :inAvgRating");
        query.setParameter("inAvgRating", avgRating);
        return query.getResultList();
    }

    @Override
    public Student studentLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Student student = retrieveStudentByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + student.getSalt()));

            if (student.getPassword().equals(passwordHash)) {
                return student;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (StudentNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Student>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
