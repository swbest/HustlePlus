/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AdminStaff;
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
import util.exception.AdminStaffNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateAdminStaffException;
import util.exception.UserEmailExistsException;
import util.security.CryptographicHelper;

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
    public Long createNewAdminStaff(AdminStaff newAdminStaff) throws UserEmailExistsException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<AdminStaff>> constraintViolations = validator.validate(newAdminStaff);

            if (constraintViolations.isEmpty()) {
                
                System.out.println("Create Admin (Before Persist)");
                em.persist(newAdminStaff);
                em.flush();
                
                System.out.println("Create Admin (After Persist)");


                return newAdminStaff.getUserId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new UserEmailExistsException("User email exists, please try again!");
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
        if (adminStaff != null && adminStaff.getUserId() != null) {
            Set<ConstraintViolation<AdminStaff>> constraintViolations = validator.validate(adminStaff);

            if (constraintViolations.isEmpty()) {
                AdminStaff adminStaffToUpdate = retrieveAdminStaffByAdminStaffId(adminStaff.getUserId());
                adminStaffToUpdate.setUsername(adminStaff.getUsername());
                adminStaffToUpdate.setPassword(adminStaff.getPassword());
                adminStaffToUpdate.setIcon(adminStaff.getIcon());
                adminStaffToUpdate.setEmail(adminStaff.getEmail());
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

    @Override
    public AdminStaff retrieveAdminStaffByUsername(String username) throws AdminStaffNotFoundException {
        Query query = em.createQuery("SELECT a FROM AdminStaff a WHERE a.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (AdminStaff) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new AdminStaffNotFoundException("Admin Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public AdminStaff adminStaffLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            AdminStaff adminStaff = retrieveAdminStaffByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + adminStaff.getSalt()));

            if (adminStaff.getPassword().equals(passwordHash)) {
                return adminStaff;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (AdminStaffNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
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
