/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Skill;
import entity.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import util.exception.SkillNotFoundException;
import util.exception.StudentAssignedToProjectException;
import util.exception.StudentNotFoundException;
import util.exception.SuspendStudentException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateStudentException;
import util.exception.VerifyStudentException;
import util.security.CryptographicHelper;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class StudentSessionBean implements StudentSessionBeanLocal {

    @EJB
    private SkillSessionBeanLocal skillSessionBeanLocal;

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StudentSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createStudentAccount(Student newStudent, List<Long> skillIds) throws SkillNotFoundException, StudentAssignedToProjectException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Student>> constraintViolations = validator.validate(newStudent);

            if (constraintViolations.isEmpty()) {
                newStudent.setIsSuspended(Boolean.FALSE);
                newStudent.setAvgRating(0.0);

                //Associate skills with student 
                List<Skill> skills = new ArrayList();
                for (Long skillIdsToSet : skillIds) {
                    skills.add(skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToSet));
                    skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToSet).addStudent(newStudent);
                }
                newStudent.setSkills(skills);

                em.persist(newStudent);
                em.flush();
                return newStudent.getUserId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new StudentAssignedToProjectException("Student name exists, please try again!");
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
            System.out.println("Student Session Bean updateStudent(): " + student.getUsername());

            if (constraintViolations.isEmpty()) {
                Student studentToUpdate = retrieveStudentByStudentId(student.getUserId());
                studentToUpdate.setUsername(student.getUsername());
                studentToUpdate.setIcon(student.getIcon());
                studentToUpdate.setEmail(student.getEmail());
                studentToUpdate.setName(student.getName());
                studentToUpdate.setResume(student.getResume());
                studentToUpdate.setDescription(student.getDescription());
                studentToUpdate.setBankAccountName(student.getBankAccountName());
                studentToUpdate.setBankAccountNumber(student.getBankAccountNumber());


                /* not in profile
                studentToUpdate.setPassword(newPassword);
                studentToUpdate.setAvgRating(student.getAvgRating());
                studentToUpdate.setIsVerified(student.getIsVerified());
                studentToUpdate.setIsSuspended(student.getIsSuspended());

                studentToUpdate.setSkills(student.getSkills());
                List<Skill> skills = student.getSkills();
                for (Skill skill : skills) {
                    skill.addStudent(student);
                }
                studentToUpdate.setAvgRating(student.getAvgRating());
                studentToUpdate.setIsVerified(student.getIsVerified());
                studentToUpdate.setIsSuspended(student.getIsSuspended());
                studentToUpdate.setTeams(student.getTeams());
                List<Team> teams = student.getTeams();
                for (Team team : teams) {
                    team.addStudent(student);
                }
                studentToUpdate.setCompanyReviews(student.getCompanyReviews());
                studentToUpdate.setStudentReviews(student.getStudentReviews());
                studentToUpdate.setPayments(student.getPayments());
                studentToUpdate.setApplications(student.getApplications());
                studentToUpdate.setProjects(student.getProjects());
                 */
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new StudentNotFoundException("Student Id not provided for student to be updated");
        }
    }

    @Override
    public void updatePassword(Student student, String password) throws StudentNotFoundException, UpdateStudentException, InputDataValidationException {
        if (student != null && student.getUserId() != null) {
            Set<ConstraintViolation<Student>> constraintViolations = validator.validate(student);
            if (constraintViolations.isEmpty()) {
                Student studentToUpdate = retrieveStudentByStudentId(student.getUserId());
                studentToUpdate.setPassword(password);
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new StudentNotFoundException("Student id: " + student.getUserId() + " does not exists!");
        }
    }

    @Override
    public void deleteStudentAccount(Long studentId) throws StudentNotFoundException, DeleteStudentException {
        Student studentToRemove = retrieveStudentByStudentId(studentId);
        if (studentToRemove.getTeams().isEmpty() && studentToRemove.getCompanyReviews().size() == 0) {
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

        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new StudentNotFoundException("No students were found by that name!");
        }
    }

    @Override
    public List<Student> retrieveStudentsBySkills(String skillTitle) throws StudentNotFoundException {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.skills.title = :inSkillTitle");
        query.setParameter("inSkillTitle", skillTitle);

        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new StudentNotFoundException("No students were found with those skills!");
        }
    }

    @Override
    public List<Student> retrieveStudentsByAvgRating(Double avgRating) throws StudentNotFoundException {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.avgRating = :inAvgRating");
        query.setParameter("inAvgRating", avgRating);

        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new StudentNotFoundException("No students were found for that rating!");
        }
    }

    @Override
    public List<Student> filterStudentsBySkills(List<Long> skillIds) {
        List<Student> students = new ArrayList<>();

        if (skillIds == null || skillIds.isEmpty()) {
            return students;
        } else {
            // if (condition.equals("OR")) {

            //Query query = em.createQuery("SELECT DISTINCT st FROM Student st AS st LEFT JOIN Skill_student AS skst ON st.userId = skst. WHERE st.skills_skillId IN :inSkillIds");
            Query query = em.createQuery("SELECT st FROM Student st, IN (st.skills) s WHERE s.skillId IN :inSkillIds");
            query.setParameter("inSkillIds", skillIds);
            students = query.getResultList();

//            } else // AND
//            {
//                String selectClause = "SELECT st FROM Student st";
//                String whereClause = "";
//                Boolean firstSkill = true;
//                Integer skillCount = 1;
//
//                for (Long skillId : skillIds) {
//                    selectClause += ", IN (st.skills) s" + skillCount;
//
//                    if (firstSkill) {
//                        whereClause = "WHERE st1.skillId = " + skillId;
//                        firstSkill = false;
//                    } else {
//                        whereClause += " AND st" + skillCount + ".skillId = " + skillId;
//                    }
//
//                    skillCount++;
//                }
//                String jpql = selectClause + " " + whereClause + " ORDER BY st.name ASC";
//                Query query = em.createQuery(jpql);
//                students = query.getResultList();
//            }
//            for (Student student : students) {
//                student.getSkills().size();
//            }
            Collections.sort(students, new Comparator<Student>() {
                public int compare(Student st1, Student st2) {
                    return st1.getName().compareTo(st2.getName());
                }
            });

            return students;
        }
    }

    @Override
    public void verifyStudent(Long studentId) throws StudentNotFoundException, VerifyStudentException {
        try {
            Student studentToVerify = retrieveStudentByStudentId(studentId);

            if (studentToVerify.getIsVerified() == false) {
                System.out.println("*****FALSE******");
                studentToVerify.setIsVerified(Boolean.TRUE);
            } else {
                System.out.println("*****TRUE******");
                throw new VerifyStudentException("Student has been verified!");
            }
        } catch (StudentNotFoundException ex) {
            throw new StudentNotFoundException("Student id does not exist!");
        }
    }

    @Override
    public void suspendStudent(Long studentId) throws StudentNotFoundException, SuspendStudentException {
        Student studentToSuspend = retrieveStudentByStudentId(studentId);

        if (studentToSuspend != null) {

            if (studentToSuspend.getIsSuspended() == false) {
                studentToSuspend.setIsSuspended(Boolean.TRUE);
            } else {
                throw new SuspendStudentException("Student has been suspended");
            }
        } else {
            throw new StudentNotFoundException("Student Id does not exist");
        }
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

    @Override
    public List<Student> searchStudentsByName(String searchString) {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.name LIKE :inSearchString ORDER BY s.userId ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Student> students = query.getResultList();

        return students;
    }

    @Override
    public void addSkillToStudent(Long studentId, Long skillId) throws StudentNotFoundException, SkillNotFoundException {
        Student student = retrieveStudentByStudentId(studentId);
        Skill skill = skillSessionBeanLocal.retrieveSkillBySkillId(skillId);

        student.getSkills().add(skill);
        skill.getStudents().add(student);
    }

    @Override
    public void disassociateProjectSkill(Long studentId, Long skillId) throws StudentNotFoundException, SkillNotFoundException {
        Student student = retrieveStudentByStudentId(studentId);
        Skill skill = skillSessionBeanLocal.retrieveSkillBySkillId(skillId);

        student.getSkills().remove(skill);
        skill.getStudents().remove(student);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Student>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    public String uploadResume(Long studentId, String resume) {
        try {
            Student currStudent = retrieveStudentByStudentId(studentId);
            currStudent.setResume(resume);
        } catch (StudentNotFoundException ex) {
            Logger.getLogger(StudentSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Student Id: " + studentId + " resume upload";
    }
}
