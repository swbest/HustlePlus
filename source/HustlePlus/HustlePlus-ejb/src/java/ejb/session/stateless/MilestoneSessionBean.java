/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
import entity.Project;
import java.util.ArrayList;
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
import util.exception.InputDataValidationException;
import util.exception.MilestoneIdExistException;
import util.exception.MilestoneNotFoundException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMilestoneException;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class MilestoneSessionBean implements MilestoneSessionBeanLocal {

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @EJB
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    private List<Milestone> milestoneList;

    public MilestoneSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<Project> retrieveMilestonesByProjectId(Long projectId) throws MilestoneNotFoundException {
        Query query = em.createQuery("SELECT DISTINCT m FROM Milestone m JOIN Project p WHERE p.projectId = :inProjectId");
        query.setParameter("inProjectId", projectId);
        try {
            return query.getResultList();
        } catch (NoResultException ex) {
            throw new MilestoneNotFoundException("No milestones available for this project!");
        }
    }

    @Override
    public Long createNewMilestone(Milestone newMilestone, Long projectId) throws MilestoneIdExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException {
        try {
            Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(newMilestone);

            if (constraintViolations.isEmpty()) {
                try {
                    Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                    newMilestone.setProject(project);
                    project.addMilestone(newMilestone);
                    em.persist(newMilestone);
                    em.flush();

                    return newMilestone.getMilestoneId();
                } catch (ProjectNotFoundException ex) {
                    throw new ProjectNotFoundException("Project Not Found for ID: " + projectId);
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new MilestoneIdExistException("Milestone name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Milestone retrieveMilestoneByMilestoneId(Long milestoneId) throws MilestoneNotFoundException {
        Milestone milestone = em.find(Milestone.class, milestoneId);

        if (milestone != null) {
            return milestone;
        } else {
            throw new MilestoneNotFoundException("Milestone ID " + milestoneId + " does not exist!");
        }

    }

    @Override
    public void updateMilestone(Milestone milestone) throws MilestoneNotFoundException, UpdateMilestoneException, InputDataValidationException {
        if (milestone != null && milestone.getMilestoneId() != null) {
            Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(milestone);

            if (constraintViolations.isEmpty()) {
                Milestone milestoneToUpdate = retrieveMilestoneByMilestoneId(milestone.getMilestoneId());
                milestoneToUpdate.setTitle(milestone.getTitle());
                milestoneToUpdate.setDescription(milestone.getDescription());
                //milestoneToUpdate.setPayments(milestone.getPayments());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new MilestoneNotFoundException("Milestone Id not provided for milestone to be updated");
        }
    }

    @Override
    public void deleteMilestone(Long milestoneId) throws MilestoneNotFoundException {
        Milestone milestoneToRemove = retrieveMilestoneByMilestoneId(milestoneId);
        em.remove(milestoneToRemove);
    }

    @Override
    public List<Milestone> retrieveAllMilestones() {
        Query query = em.createQuery("SELECT m FROM Milestone m");

        return query.getResultList();

    }

    @Override
    public List<Milestone> retrieveMilestonesByProject(Long projectId) throws MilestoneNotFoundException {
        Query query = em.createQuery("SELECT m FROM Milestone m WHERE m.project.projectId =:pid ");
        query.setParameter("pid", projectId);
        if (query.getResultList().size() == 0) {
            throw new MilestoneNotFoundException("No milestones available for this project!");
        } else {
            return query.getResultList();
        }
    }

    @Override
    public List<Milestone> retrieveMilestonesByCompany(Long companyId) throws ProjectNotFoundException {

        try {
            //retrieve all projects of company 
            List<Project> projects = projectSessionBeanLocal.retrieveProjectsByCompany(companyId);
//        List<Milestone> milestonesForOneProject = new ArrayList(); 
            List<Milestone> msList = new ArrayList();
//        for (Project p:projects) {
//            milestonesForOneProject = p.getMilestones();
//            for(Milestone m:milestonesForOneProject) {
//                System.out.println(m.getMilestoneId());
//                milestoneList.add(m); 
//            }
//        } 

            //in milestone table, the milestone must have these project ids 
            //iterate through all milestones, if milestone has that id
            for (Project p : projects) {
                Query query = em.createQuery("SELECT m FROM Milestone m WHERE m.project.projectId =:pid ");
                query.setParameter("pid", p.getProjectId());
                List<Milestone> milestones = (List<Milestone>) query.getResultList();
                for (Milestone m : milestones) {
                    msList.add(m);
                    System.out.println("m.getP.getPID " + m.getProject().getProjectId());
                }

            }

            return msList;
            //retrieve all projects of a company first 
            //iterate through list, if projectId falls in milestone table retrieve it 
        } catch (ProjectNotFoundException ex) {
            throw new ProjectNotFoundException("Project Not Found for ID: ");

        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Milestone>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
