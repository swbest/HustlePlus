/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Skill;
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
import util.exception.DeleteSkillException;
import util.exception.InputDataValidationException;
import util.exception.SkillNameExistsException;
import util.exception.SkillNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSkillException;

/**
 *
 * @author dtjldamien
 */
@Stateless
public class SkillSessionBean implements SkillSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public SkillSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewSkill(Skill newSkill) throws UnknownPersistenceException, InputDataValidationException, SkillNameExistsException {
        try {
            Set<ConstraintViolation<Skill>> constraintViolations = validator.validate(newSkill);

            if (constraintViolations.isEmpty()) {
                em.persist(newSkill);
                em.flush();

                return newSkill.getSkillId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new SkillNameExistsException("Skill name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Skill retrieveSkillBySkillId(Long skillId) throws SkillNotFoundException {
        Skill skill = em.find(Skill.class, skillId);

        if (skill != null) {
            return skill;
        } else {
            throw new SkillNotFoundException("Skill ID " + skillId + " does not exist!");
        }
    }

    @Override
    public void updateSkill(Skill skill) throws SkillNotFoundException, UpdateSkillException, InputDataValidationException {
        if (skill != null && skill.getSkillId() != null) {
            Set<ConstraintViolation<Skill>> constraintViolations = validator.validate(skill);

            if (constraintViolations.isEmpty()) {
                Skill skillToUpdate = retrieveSkillBySkillId(skill.getSkillId());
                skillToUpdate.setProjects(skill.getProjects());
                skillToUpdate.setStudents(skill.getStudents());
                skillToUpdate.setTitle(skill.getTitle());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new SkillNotFoundException("Skill Id not provided for student to be updated");
        }
    }
    


    @Override
    public void deleteSkill(Long skillId) throws SkillNotFoundException, DeleteSkillException {
        Skill skillToRemove = retrieveSkillBySkillId(skillId);
        if (skillToRemove.getProjects().size() == 0 && skillToRemove.getStudents().size() == 0) {
            em.remove(skillToRemove);
        } else {
            throw new DeleteSkillException("Skill ID " + skillId + " is associated with existing projects and students and cannot be deleted!");
        }
        em.remove(skillToRemove);
    }

    @Override
    public List<Skill> retrieveAllSkills() {
        Query query = em.createQuery("SELECT s FROM Skill s");
        return query.getResultList();
    }

    @Override
    public Skill retrieveSkillsBySkillTitle(String skillTitle) throws SkillNotFoundException {
        Query query = em.createQuery("SELECT s FROM Skill s WHERE s.title = :inSkillTitle");
        query.setParameter("inSkillTitle", skillTitle);

        try {
            return (Skill) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new SkillNotFoundException("Skill title " + skillTitle + " does not exist!");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Skill>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

}
