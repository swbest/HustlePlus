/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
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
import util.enumeration.AccessRightEnum;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;
import util.security.CryptographicHelper;

/**
 *
 * @author amanda
 */
@Stateless
public class CompanySessionBean implements CompanySessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CompanySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Company createNewCompany(Company newCompany) throws CompanyNameExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Company>> constraintViolations = validator.validate(newCompany);

            if (constraintViolations.isEmpty()) {
                
                newCompany.setIsSuspended(Boolean.FALSE);
                newCompany.setIsVerified(Boolean.FALSE);
                newCompany.setAccessRightEnum(AccessRightEnum.COMPANY);
                em.persist(newCompany);
                em.flush();

                return newCompany;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CompanyNameExistException("Company name exists, please try again!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Company> retrieveAllCompanies() {
        Query query = em.createQuery("SELECT c FROM Company c");

        return query.getResultList();
    }

    @Override
    public Company retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException {
        Company comp = em.find(Company.class, companyId);

        if (comp != null) {
            return comp;
        } else {
            throw new CompanyNotFoundException("Company ID " + companyId + " does not exist!");
        }
    }

    @Override
    public Company retrieveCompanyByUsername(String username) throws CompanyNotFoundException {
        Query query = em.createQuery("SELECT c FROM Company c WHERE c.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Company) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CompanyNotFoundException("Company Username " + username + " does not exist!");
        }
    }

    @Override
    public Company companyLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Company company = retrieveCompanyByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + company.getSalt()));

            if (company.getPassword().equals(passwordHash)) {
                return company;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (CompanyNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateCompany(Company company) throws CompanyNotFoundException, UpdateCompanyException, InputDataValidationException {
        if (company != null && company.getUserId() != null) {
            Set<ConstraintViolation<Company>> constraintViolations = validator.validate(company);

            if (constraintViolations.isEmpty()) {
                Company companyToUpdate = retrieveCompanyByCompanyId(company.getUserId());
                companyToUpdate.setUsername(company.getUsername());
                companyToUpdate.setPassword(company.getPassword());
                companyToUpdate.setIcon(company.getIcon());
                companyToUpdate.setEmail(company.getEmail());
                companyToUpdate.setName(company.getName());
                companyToUpdate.setDescription(company.getDescription());
                companyToUpdate.setAvgRating(company.getAvgRating());
                companyToUpdate.setIsVerified(company.getIsVerified());
                companyToUpdate.setIsSuspended(company.getIsSuspended());
                companyToUpdate.setProjects(company.getProjects());
                companyToUpdate.setStudentReviews(company.getStudentReviews());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new CompanyNotFoundException("Company Id not provided for company to be updated");
        }
    }

    //should companies be able to delete their accounts even when they still have listed projects *but have no one doing them*?
    @Override
    public void deleteCompany(Long companyId) throws CompanyNotFoundException, DeleteCompanyException {
        Company companyToRemove = retrieveCompanyByCompanyId(companyId);
        if (companyToRemove.getProjects().isEmpty() && companyToRemove.getStudentReviews().isEmpty()) {
            em.remove(companyToRemove);
        } else {
            throw new DeleteCompanyException("Company ID " + companyId + " is associated with existing projects and reviews and cannot be deleted!");
        }
    }

    // searching for company) rating/ company's name
    @Override
    public List<Company> retrieveCompaniesByName(String cname) {
        Query query = em.createQuery("SELECT c FROM Company c WHERE c.Name LIKE '%cname%'");
        query.setParameter("inName", cname);
        return query.getResultList();
    }

    @Override
    public List<Company> retrieveCompaniesByAvgRating(Double avgRating) {
        Query query = em.createQuery("SELECT c FROM Company c WHERE c.avgRating = :inAvgRating");
        query.setParameter("inAvgRating", avgRating);
        return query.getResultList();
    }
    
    public void verifyCompany(Company company) {
         
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Company>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        return msg;
    }
}
