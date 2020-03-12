/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
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
import util.exception.MilestoneIdExistException;
import util.exception.MilestoneNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMilestoneException;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class MilestoneSessionBean implements MilestoneSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MilestoneSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Milestone createNewMilestone(Milestone newMilestone) throws MilestoneIdExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(newMilestone);

            if (constraintViolations.isEmpty()) {
                em.persist(newMilestone);
                em.flush();

                return newMilestone;
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
                milestoneToUpdate.setDescription(milestone.getDescription());
                milestoneToUpdate.setHasCleared(milestone.getHasCleared());
                milestoneToUpdate.setPayments(milestone.getPayments());
                milestoneToUpdate.setProject(milestone.getProject());
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

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Milestone>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
