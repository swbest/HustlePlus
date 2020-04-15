/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Project;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.DeleteProjectException;
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

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    public ProjectSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewProject(Project newProject, Long companyId) throws CompanyNotVerifiedException, CompanySuspendedException, UnknownPersistenceException, InputDataValidationException, ProjectNameExistException, CompanyNotFoundException {
        try {
                                System.out.println("PSB0");

            Set<ConstraintViolation<Project>> constraintViolations = validator.validate(newProject);
                                System.out.println("PSB0.5");


            if (constraintViolations.isEmpty()) {
                try {
                    System.out.println("PSB1");
                    Company company = companySessionBeanLocal.retrieveCompanyByCompanyId(companyId);
                    System.out.println("PSB2");
                    if (company.getIsVerified() == false) {
                        throw new CompanyNotVerifiedException("Company is not yet verified! Please wait a few days for admin staff to verify.");
                    }
                    if (company.getIsSuspended() == true) {
                        throw new CompanySuspendedException("Company is suspended. Please contact admin staff for details.");
                    }
                    newProject.setCompany(company);
                    System.out.println("PSB3");
                    company.getProjects().add(newProject);
                    System.out.println("PSB4");
                    em.persist(newProject);
                    em.flush();

                    return newProject.getProjectId();
                } catch (CompanyNotFoundException ex) {
                    throw new CompanyNotFoundException("Company Not Found for ID: " + companyId);
                }
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
    public List<Project> retrieveAllProjects() {
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
    public void updateProject(Project project, Long companyId) throws CompanyNotFoundException, ProjectNotFoundException, UpdateProjectException, InputDataValidationException {
        if (project != null && project.getProjectId() != null) {
            Set<ConstraintViolation<Project>> constraintViolations = validator.validate(project);

            if (constraintViolations.isEmpty()) {
                try {
                    Company company = companySessionBeanLocal.retrieveCompanyByCompanyId(companyId);
                    Project projectToUpdate = retrieveProjectByProjectId(project.getProjectId());
                    projectToUpdate.setProjectName(project.getProjectName());
                    projectToUpdate.setJobValue(project.getJobValue());
                    projectToUpdate.setNumStudentsRequired(project.getNumStudentsRequired());
                    projectToUpdate.setProjectDescription(project.getProjectDescription());
                    projectToUpdate.setStartDate(project.getStartDate());
                    projectToUpdate.setEndDate(project.getEndDate());
                    projectToUpdate.setSkills(project.getSkills());
                    projectToUpdate.setCompany(company);
                    projectToUpdate.setTeam(project.getTeam());
                    projectToUpdate.setMilestones(project.getMilestones());
                    projectToUpdate.setReviews(project.getReviews());
                    projectToUpdate.setApplications(project.getApplications());
                } catch (CompanyNotFoundException ex) {
                    throw new CompanyNotFoundException("Company Not Found for ID: " + companyId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ProjectNotFoundException("Project Id not provided for project to be updated");
        }
    }

    @Override
    public void deleteProject(Long projectId) throws ProjectNotFoundException, DeleteProjectException {
        Project projectToRemove = retrieveProjectByProjectId(projectId);
        if (projectToRemove.getTeam().getNumStudents() == 0 && projectToRemove.getReviews().size() == 0 || projectToRemove.getTeam().getTeamId() == null) {
            em.remove(projectToRemove);
        } else {
            throw new DeleteProjectException("Project Id " + projectId + " is associated with existing teams and reviews and cannot be deleted!");
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Project>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

    // searching for existing project) project name/ company name/project skills required
    @Override
    public List<Project> retrieveProjectsByName(String pname) throws ProjectNotFoundException {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.projectName LIKE '%inProjectName%'");
        query.setParameter("inProjectName", pname);
        
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new ProjectNotFoundException("No projects were found by that name!");
        }
    }

    @Override
    public List<Project> retrieveProjectsByCompany(String cname) throws ProjectNotFoundException {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.company.companyName LIKE '%inCompanyName%'");
        query.setParameter("inCompanyName", cname);
        
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new ProjectNotFoundException("No projects were found by that Company!");
        }
    }

    @Override
    public List<Project> retrieveProjectsBySkills(String skillTitle) throws ProjectNotFoundException {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.skills.title = :inSkillTitle");
        query.setParameter("inSkillTitle", skillTitle);
        
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new ProjectNotFoundException("No projects were found by that skill!");
        }
    }
    
    @Override
    public List<Project> filterProjectsBySkills(List<Long> skillIds, String condition)
    {
        List<Project> projects = new ArrayList<>();
        
        if(skillIds == null || skillIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR")))
        {
            return projects;
        }
        else
        {
            if(condition.equals("OR"))
            {
                Query query = em.createQuery("SELECT DISTINCT p FROM Project p, IN (p.skills) s WHERE s.skillId IN :inSkillIds ORDER BY p.projectName ASC");
                query.setParameter("inSkillIds", skillIds);
                projects = query.getResultList();                                                          
            }
            else // AND
            {
                String selectClause = "SELECT p FROM Project p";
                String whereClause = "";
                Boolean firstSkill = true;
                Integer skillCount = 1;

                for(Long skillId : skillIds)
                {
                    selectClause += ", IN (p.skills) s" + skillCount;

                    if(firstSkill)
                    {
                        whereClause = "WHERE p1.skillId = " + skillId;
                        firstSkill = false;
                    }
                    else
                    {
                        whereClause += " AND p" + skillCount + ".skillId = " + skillId; 
                    }
                    
                    skillCount++;
                }
                
                String jpql = selectClause + " " + whereClause + " ORDER BY p.projectName ASC";
                Query query = em.createQuery(jpql);
                projects = query.getResultList();                                
            }
            
            for(Project project : projects)
            {
                project.getSkills().size();
            }
            
            Collections.sort(projects, new Comparator<Project>()
            {
                public int compare(Project p1, Project p2) {
                    return p1.getProjectName().compareTo(p2.getProjectName());
                }
            });
            
            return projects;
        }
    }
    
}
