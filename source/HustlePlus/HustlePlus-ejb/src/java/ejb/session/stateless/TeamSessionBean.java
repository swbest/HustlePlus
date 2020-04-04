/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Project;
import entity.Team;
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
import util.exception.DeleteTeamException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
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
    public Long createNewTeam(Team newTeam, Long projectId) throws TeamNameExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException {
        try {
            Set<ConstraintViolation<Team>> constraintViolations = validator.validate(newTeam);

            if (constraintViolations.isEmpty()) {
                try {
                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    newTeam.setProject(project);
                    project.setTeam(newTeam);
                    em.persist(newTeam);
                    em.flush();

                    return newTeam.getTeamId();
                } catch (ProjectNotFoundException ex) {
                    throw new ProjectNotFoundException("Project Not Found for ID: " + projectId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
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
    public void deleteTeam(Long teamId) throws TeamNotFoundException, DeleteTeamException {

        Team teamToRemove = retrieveTeamByTeamId(teamId);

        if (teamToRemove.getProject() == null) {
            em.remove(teamToRemove);
        } else {
            throw new DeleteTeamException("Team ID " + teamId + " is associated with an existing project and cannot be deleted!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Team>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
