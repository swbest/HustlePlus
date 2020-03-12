/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Project;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author sw_be
 */
@Stateless
public class ProjectSessionBean implements ProjectSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProjectSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Project createNewProject(Project newProject) throws UnknownPersistenceException, InputDataValidationException, ProjectNameExistException {
        try {
            Set<ConstraintViolation<Project>> constraintViolations = validator.validate(newProject);

            if (constraintViolations.isEmpty()) {
                em.persist(newProject);
                em.flush();

                return newProject;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ProjectNameExistException("Company name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Project> retrieveAllProject() {
        Query query = em.createQuery("SELECT p FROM Project p");

        return query.getResultList();
    }

    @Override
    public Project retrieveProjectByProjectId(Long projectId) throws ProjectNotFoundException {
        Project project = em.find(Project.class, projectId);

        if (project != null) {
            return project;
        } else {
            throw new ProjectNotFoundException("Project ID " + projectId + " does not exist!");
        }
    }

    @Override
    public void updateProject(Project project) throws ProjectNotFoundException, UpdateProjectException, InputDataValidationException {
        if (project != null && project.getProjectId()!= null) {
            Set<ConstraintViolation<Project>> constraintViolations = validator.validate(project);

            if (constraintViolations.isEmpty()) {
                Project projectToUpdate = retrieveProjectByProjectId(project.getProjectId());
                projectToUpdate.setProjectName(project.getProjectName());
                projectToUpdate.setJobValue(project.getJobValue());
                projectToUpdate.setNoStudentsRequired(project.getNoStudentsRequired());
                projectToUpdate.setProjectDescription(project.getProjectDescription());
                projectToUpdate.setStartDate(project.getStartDate());
                projectToUpdate.setEndDate(project.getEndDate());
                projectToUpdate.setCompany(project.getCompany());
                projectToUpdate.setTeam(project.getTeam());
                projectToUpdate.setMilestones(project.getMilestones());
                projectToUpdate.setReviews(project.getReviews());
                        
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ProjectNotFoundException("Project Id not provided for project to be updated");
        }
    }

    @Override
    public void deleteProject(Long projectId) throws ProjectNotFoundException {
        Project projectToRemove = retrieveProjectByProjectId(projectId);
        em.remove(projectToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Project>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
        
}
