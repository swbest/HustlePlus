/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.DeleteCompanyException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCompanyException;

/**
 *
 * @author amanda
 */
@Local
public interface CompanySessionBeanLocal {
    
    public Company createNewCompany(Company newCompany) throws CompanyNameExistException, UnknownPersistenceException, InputDataValidationException;
    
    public List<Company> retrieveAllCompanies();
    
    public Company retrieveCompanyByCompanyId(Long companyId) throws CompanyNotFoundException;
        
    public void deleteCompany(Long companyId) throws CompanyNotFoundException, DeleteCompanyException;
    
    public Company retrieveCompanyByUsername(String username) throws CompanyNotFoundException;

    public void updateCompany(Company company) throws CompanyNotFoundException, UpdateCompanyException, InputDataValidationException;

    public Company companyLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
