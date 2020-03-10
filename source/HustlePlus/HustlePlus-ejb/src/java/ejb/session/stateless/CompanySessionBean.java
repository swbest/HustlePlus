/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
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
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;

/**
 *
 * @author amanda
 */
@Stateless
@Local
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
    public Long createNewCompany(Company newCompany) throws CompanyNameExistException, UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            Set<ConstraintViolation<Company>>constraintViolations = validator.validate(newCompany);
        
            if(constraintViolations.isEmpty())
            {
                em.persist(newCompany);
                em.flush();

                return newCompany.getCompanyId();
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }            
        }
        catch(PersistenceException ex)
        {
            if(ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException"))
            {
                if(ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException"))
                {
                    throw new CompanyNameExistException("Company name exists, please try again!");
                }
                else
                {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
            else
            {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    
    @Override
    public List<Company> retrieveAllCompanies()
    {
        Query query = em.createQuery("SELECT c FROM Company c");
        
        return query.getResultList();
    }
    
    
    @Override
    public Company retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException
    {
        Company comp = em.find(Company.class, companyId);
        
        if(comp != null)
        {
            return comp;
        }
        else
        {
            throw new CompanyNotFoundException("Company ID " + companyId + " does not exist!");
        }
    }
    
    @Override
    public void updateCompany(Company company, Long studentReviewId, List<Long> projectIds) throws CompanyNotFoundException, /*ProjectNotFoundException, StudentReviewNotFoundException,*/ UpdateCompanyException, InputDataValidationException
    {
        /*if(company != null && company.getCompanyId()!= null)
        {
            Set<ConstraintViolation<Company>>constraintViolations = validator.validate(company);
        
            if(constraintViolations.isEmpty())
            {
                Company companyToUpdate = retrieveCompanyByCompanyId(company.getCompanyId());

                if(companyToUpdate.getName().equals(company.getName()))
                {
                    if(studentReviewId != null && (!companyToUpdate.getStudentReview().getReviewId().equals(studentReviewId)))
                    {
                        StudentReview studentReviewToUpdate = reviewSessionBeanLocal.retrieveStudentReviewByReviewId(studentReviewId);
                        companyToUpdate.setStudentReview(studentReviewToUpdate);
                    }
                    
                    if(projectIds != null)
                    {
                        for(Project project:companyToUpdate.getProjects())
                        {
                            project.getCompany().remove(companyToUpdate);
                        }
                        
                        companyToUpdate.getProjects().clear();
                        
                        for(Long projectId:projectIds)
                        {
                            Project project = projectSessionBeanLocal.retrieveProjectByProjectId(projectId);
                            companyToUpdate.addProject(Project);
                        }
                    }
                    
                    companyToUpdate.setDescription(company.getDescription());
                    companyToUpdate.setPassword(company.getPassword());
                    companyToUpdate.setIcon(company.getIcon());
                    companyToUpdate.setEmail(company.getEmail());
                    //companyToUpdate.setAvgRating((company.getAvgRating()));
                }
                else
                {
                    throw new UpdateCompanyException("Name of company to be updated does not match the existing record!");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new CompanyNotFoundException("Company ID not provided for company to be updated");
        }*/
    }
    
    
    //should companies be able to delete their accounts even when they still have listed projects *but have no one doing them*?
    @Override
    public void deleteCompany(Long companyId) throws CompanyNotFoundException, DeleteCompanyException
    {
        
        Company companyToRemove = retrieveCompanyByCompanyId(companyId);
        
//        if(companyToRemove.getProject() == null)
//        {
            em.remove(companyToRemove);
//        }
//        else
//        {
//            throw new DeleteCompanyException("Company ID " + companyId + " is associated with an existing project and cannot be deleted!");
//        }
        
    }
    
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Company>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
}
