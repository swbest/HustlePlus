/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Student;
import entity.Team;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.DeleteTeamException;
import util.exception.InputDataValidationException;
import util.exception.StudentAlreadyInTeamException;
import util.exception.StudentNotFoundException;
import util.exception.TeamNameExistException;
import util.exception.TeamNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateTeamException;

/**
 *
 * @author amanda
 */
@Stateless
public class TeamSessionBean implements TeamSessionBeanLocal {

    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @EJB
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    public TeamSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewTeam(Team newTeam, Long studentId) throws StudentNotFoundException, TeamNameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
            newTeam.addStudent(student);
            student.addTeam(newTeam);
            em.persist(newTeam);
            em.flush();
            return newTeam.getTeamId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new TeamNameExistException("Team name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } catch (StudentNotFoundException ex) {
            throw new StudentNotFoundException(ex.getMessage());
        }
    }

    @Override
    public List<Team> retrieveAllTeams() {
        Query query = em.createQuery("SELECT t FROM Team t");

        return query.getResultList();
    }

    @Override
    public Team retrieveTeamByTeamId(Long teamId) throws TeamNotFoundException {
        Team team = em.find(Team.class, teamId);

        if (team != null) {
            return team;
        } else {
            throw new TeamNotFoundException("Team ID " + teamId + " does not exist!");
        }
    }

    @Override
    public List<Team> retrieveTeamsByStudentId(Long studentId) throws StudentNotFoundException {
        Query query = em.createQuery("SELECT s.teams FROM Student s WHERE s.userId = :inStudentId");
        query.setParameter("inStudentId", studentId);

        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new StudentNotFoundException("No students were found by that name!");
        }
    }

    @Override
    public void updateTeam(Team team, Long projectId, List<Long> studentIds) throws TeamNotFoundException, /*ProjectNotFoundException, StudentNotFoundException,*/ UpdateTeamException, InputDataValidationException {
        if (team != null && team.getTeamId() != null) {
            Set<ConstraintViolation<Team>> constraintViolations = validator.validate(team);

            if (constraintViolations.isEmpty()) {
                Team teamToUpdate = retrieveTeamByTeamId(team.getTeamId());
                teamToUpdate.setTeamName(team.getTeamName());
                teamToUpdate.setNumStudents(team.getNumStudents());
                teamToUpdate.setStudents(team.getStudents());
                teamToUpdate.setProject(team.getProject());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new TeamNotFoundException("Team ID not provided for company to be updated");
        }
    }

    @Override
    public Long addStudentToTeam(Long teamId, Long studentId) throws StudentAlreadyInTeamException, TeamNotFoundException, StudentNotFoundException, UpdateTeamException, InputDataValidationException {
        Team team = retrieveTeamByTeamId(teamId);
        Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
        if (student.getTeams().contains(team)) {
            throw new StudentAlreadyInTeamException("The student to be added is already in your team: " + team.getTeamName());
        }
        team.addStudent(student);
        student.addTeam(team);
        return team.getTeamId();
    }

    @Override
    public Long removeStudentFromTeam(Long teamId, Long studentId) throws TeamNotFoundException, StudentNotFoundException, UpdateTeamException, InputDataValidationException {
        Team team = retrieveTeamByTeamId(teamId);
        Student student = studentSessionBeanLocal.retrieveStudentByStudentId(studentId);
        team.removeStudent(student);
        student.removeTeam(team);
        return team.getTeamId();
    }

    @Override
    public void deleteTeam(Long teamId) throws TeamNotFoundException, DeleteTeamException {
        Team teamToRemove = retrieveTeamByTeamId(teamId);
        List<Student> students = teamToRemove.getStudents();
        for (Student student : students) {
            student.removeTeam(teamToRemove);
        }
        em.remove(teamToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Team>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
