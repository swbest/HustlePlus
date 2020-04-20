/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import java.io.File;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.SuspendCompanyException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;
import util.exception.VerifyCompanyException;

/**
 *
 * @author amanda
 */
@Local
public interface CompanySessionBeanLocal {
    
    public Long createNewCompany(Company newCompany) throws CompanyNameExistException, UnknownPersistenceException, InputDataValidationException;
    
    public List<Company> retrieveAllCompanies();
    
    public Company retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException;
        
    public void deleteCompany(Long companyId) throws CompanyNotFoundException, DeleteCompanyException;
    
    public Company retrieveCompanyByUsername(String username) throws CompanyNotFoundException;

    public void updateCompany(Company company) throws CompanyNotFoundException, UpdateCompanyException, InputDataValidationException;

    public Company companyLogin(String username, String password) throws InvalidLoginCredentialException;

    public List<Company> retrieveCompaniesByName(String cname) throws CompanyNotFoundException;

    public void verifyCompany(Long companyId) throws CompanyNotFoundException, VerifyCompanyException;

    public void suspendCompany(Long companyId) throws CompanyNotFoundException, SuspendCompanyException;

    public boolean checkCompany(Company company);

    public void updatePassword(Company company, String password) throws CompanyNotFoundException, UpdateCompanyException, InputDataValidationException;

    public void uploadIcon(Long companyId, String source);

    public List<Company> searchCompaniesByName(String searchString);

    public List<Company> searchCompaniesByRating(Double avgRating);

    
}
