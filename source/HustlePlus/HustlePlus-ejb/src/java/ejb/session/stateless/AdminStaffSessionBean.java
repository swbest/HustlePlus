/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AdminStaff;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.AdminStaffNameExistException;
import util.exception.AdminStaffNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAdminStaffException;

/**
 *
 * @author dtjldamien
 */
@Stateless
public class AdminStaffSessionBean implements AdminStaffSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public AdminStaffSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public AdminStaff createNewAdminStaff(AdminStaff newAdminStaff) throws AdminStaffNameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<AdminStaff>> constraintViolations = validator.validate(newAdminStaff);

            if (constraintViolations.isEmpty()) {
                em.persist(newAdminStaff);
                em.flush();

                return newAdminStaff;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new AdminStaffNameExistException("Company name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<AdminStaff> retrieveAllAdminStaff() {
        Query query = em.createQuery("SELECT a FROM AdminStaff a");

        return query.getResultList();
    }

    @Override
    public AdminStaff retrieveAdminStaffByAdminStaffId(Long adminStaffId) throws AdminStaffNotFoundException {
        AdminStaff adminStaff = em.find(AdminStaff.class, adminStaffId);

        if (adminStaff != null) {
            return adminStaff;
        } else {
            throw new AdminStaffNotFoundException("Admin Staff ID " + adminStaffId + " does not exist!");
        }
    }

    @Override
    public void updateAdminStaff(AdminStaff adminStaff) throws AdminStaffNotFoundException, UpdateAdminStaffException, InputDataValidationException {
        if (adminStaff != null && adminStaff.getAdminStaffId() != null) {
            Set<ConstraintViolation<AdminStaff>> constraintViolations = validator.validate(adminStaff);

            if (constraintViolations.isEmpty()) {
                AdminStaff adminStaffToUpdate = retrieveAdminStaffByAdminStaffId(adminStaff.getAdminStaffId());
                adminStaffToUpdate.setUsername(adminStaff.getUsername());
                adminStaffToUpdate.setPassword(adminStaff.getPassword());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new AdminStaffNotFoundException("Admin Staff Id not provided for admin staff to be updated");
        }
    }

    @Override
    public void deleteAdminStaff(Long adminStafffId) throws AdminStaffNotFoundException {
        AdminStaff adminStaffToRemove = retrieveAdminStaffByAdminStaffId(adminStafffId);
        em.remove(adminStaffToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<AdminStaff>> constraintViolations) {
        String msg = "Input data validation error!:";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
