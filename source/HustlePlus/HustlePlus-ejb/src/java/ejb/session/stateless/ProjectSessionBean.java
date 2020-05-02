/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Project;
import entity.Skill;
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
import util.exception.SkillNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author sw_be
 */
@Stateless
public class ProjectSessionBean implements ProjectSessionBeanLocal {

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ProjectSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewProject(Project newProject, Long companyId, List<Long> skillIds) throws SkillNotFoundException, CompanyNotVerifiedException, CompanySuspendedException, UnknownPersistenceException, InputDataValidationException, ProjectNameExistException, CompanyNotFoundException {
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

                    //Associate skills with project 
                    List<Skill> skills = new ArrayList();
                    for (Long skillIdsToSet : skillIds) {
                        skills.add(skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToSet));
                        skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToSet).addProject(newProject);
                    }
                    newProject.setSkills(skills);

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
        } catch (SkillNotFoundException ex) {
            throw new SkillNotFoundException("No skills were found!");
        }
    }

    @Override
    public void disassociateProjectSkill(Long projectId, Long skillId) throws ProjectNotFoundException, SkillNotFoundException {
        Project project = retrieveProjectByProjectId(projectId);
        Skill skill = skillSessionBeanLocal.retrieveSkillBySkillId(skillId);

        project.getSkills().remove(skill);
        skill.getProjects().remove(project);
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
    public void updateProject(Project project, List<Long> skillIds) throws SkillNotFoundException, ProjectNotFoundException, UpdateProjectException, InputDataValidationException {
        if (project != null && project.getProjectId() != null) {
            Set<ConstraintViolation<Project>> constraintViolations = validator.validate(project);

            if (constraintViolations.isEmpty()) {
                System.out.println("project1");

                List<Skill> skillsOfProject = project.getSkills();
                List<Project> projectsAdded = new ArrayList();
                projectsAdded.add(project);
                for (Long skillIdsToAdd : skillIds) {
                    skillsOfProject.add(skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToAdd));
                    skillSessionBeanLocal.retrieveSkillBySkillId(skillIdsToAdd).setProjects(projectsAdded);
                }
                project.setSkills(skillsOfProject);

                Project projectToUpdate = retrieveProjectByProjectId(project.getProjectId());
                System.out.println(projectToUpdate.getProjectName() + "test");
                System.out.println(project.getProjectName());
                projectToUpdate.setProjectName(project.getProjectName());
                projectToUpdate.setJobValue(project.getJobValue());
                projectToUpdate.setNumStudentsRequired(project.getNumStudentsRequired());
                projectToUpdate.setProjectDescription(project.getProjectDescription());
                projectToUpdate.setStartDate(project.getStartDate());
                projectToUpdate.setEndDate(project.getEndDate());
                projectToUpdate.setSkills(project.getSkills());
                projectToUpdate.setCompany(project.getCompany());
                projectToUpdate.setMilestones(project.getMilestones());
                projectToUpdate.setCompanyReviews(project.getCompanyReviews());
                projectToUpdate.setApplications(project.getApplications());
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
        System.out.println(projectToRemove.getProjectName());

        if (projectToRemove.getStudents().isEmpty() && projectToRemove.getCompanyReviews().isEmpty() && projectToRemove.getMilestones().isEmpty()) {
            System.out.println("deleteProjectSB");
            em.remove(projectToRemove);
        } else {
            throw new DeleteProjectException("Project Id " + projectId + " is associated with existing teams,reviews or milestones and cannot be deleted!");
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
    public List<Project> retrieveProjectsByStudentId(Long studentId) throws ProjectNotFoundException {
        Query query = em.createQuery("SELECT DISTINCT p FROM Project p INNER JOIN p.students s WHERE s.userId = :inStudentId");
        query.setParameter("inStudentId", studentId);
        try {
            System.out.println("Student ID: " + studentId + " is working for " + query.getResultList().size() + " projects");
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new ProjectNotFoundException("You are not working on any project!");
        }
    }

    @Override
    public List<Project> retrieveProjectsByCompany(Long cid) throws ProjectNotFoundException {

        System.out.println(cid);
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.company.userId = :companyId");
        query.setParameter("companyId", cid);

        List<Project> projects = (List<Project>) query.getResultList();

        try {

            for (Project p : projects) {
                System.out.println("in PSB list" + p.getProjectId());

            }

            return (List<Project>) query.getResultList();

        } catch (NoResultException ex) {
            System.out.println("PSB1");
            throw new ProjectNotFoundException("No projects were found by that Company!");
        }
    }

    @Override
    public List<Project> searchProjectsByCompany(String searchString) {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.company.name LIKE :inSearchString ORDER BY p.projectId ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Project> projects = query.getResultList();

        return projects;
    }

    @Override
    public List<Project> searchProjectsByName(String searchString) {
        Query query = em.createQuery("SELECT p FROM Project p WHERE p.projectName LIKE :inSearchString ORDER BY p.projectId ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Project> projects = query.getResultList();

        return projects;
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
    public List<Project> filterProjectByCompanies(List<Long> companyIds) {
       System.out.println("filterProjectByCompaniesPSB");
        
        List<Project> projects = new ArrayList<>();

        if (companyIds == null || companyIds.isEmpty()) {
            return projects;
        } else {
           // if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT p FROM Project p WHERE p.company IN :inCompanyIds ORDER BY p.projectId ASC");
                query.setParameter("inCompanyIds", companyIds);
                projects = query.getResultList();
//            } else // AND
//            {
//                String selectClause = "SELECT p FROM Project p";
//                String whereClause = "";
//                Boolean firstTag = true;
//               // Integer tagCount = 1;
//
//                for (Long companyId : companyIds) {
//                   // selectClause += ", IN (p.company) ce" + tagCount;
//
//                    if (firstTag) {
//                        whereClause = "WHERE p.company.userId = " + companyId;
//                        firstTag = false;
//                    } else {
//                        whereClause += " AND p.company.userId = " + companyId;
//                    }
//
//                   // tagCount++;
//                }
//
//                String jpql = selectClause + " " + whereClause + " ORDER BY p.projectId ASC";
//                Query query = em.createQuery(jpql);
//                projects = query.getResultList();
//            }
//
//            for (Project project : projects) {
//                project.getCompany();
//
//            }

            Collections.sort(projects, new Comparator<Project>() {
                public int compare(Project p1, Project p2) {
                    return p1.getProjectId().compareTo(p2.getProjectId());
                }
            });

            return projects;
        }
    }
    

    @Override
    public List<Project> filterProjectsBySkills(List<Long> skillIds, String condition) {
        List<Project> projects = new ArrayList<>();

        if (skillIds == null || skillIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return projects;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT p FROM Project p, IN (p.skills) s WHERE s.skillId IN :inSkillIds ORDER BY p.projectName ASC");
                query.setParameter("inSkillIds", skillIds);
                projects = query.getResultList();
            } else // AND
            {
                String selectClause = "SELECT p FROM Project p";
                String whereClause = "";
                Boolean firstSkill = true;
                Integer skillCount = 1;

                for (Long skillId : skillIds) {
                    selectClause += ", IN (p.skills) s" + skillCount;

                    if (firstSkill) {
                        whereClause = "WHERE p1.skillId = " + skillId;
                        firstSkill = false;
                    } else {
                        whereClause += " AND p" + skillCount + ".skillId = " + skillId;
                    }

                    skillCount++;
                }

                String jpql = selectClause + " " + whereClause + " ORDER BY p.projectName ASC";
                Query query = em.createQuery(jpql);
                projects = query.getResultList();
            }

            for (Project project : projects) {
                project.getSkills().size();
            }

            Collections.sort(projects, new Comparator<Project>() {
                public int compare(Project p1, Project p2) {
                    return p1.getProjectName().compareTo(p2.getProjectName());
                }
            });

            return projects;
        }
    }
}
