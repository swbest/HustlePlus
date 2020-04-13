/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminStaffSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import entity.AdminStaff;
import entity.Company;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AccessRightEnum;
import util.exception.CompanyNameExistException;
import util.exception.CompanyNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UserEmailExistsException;

/**
 *
 * @author dtjldamien
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    @EJB(name = "AdminStaffSessionBeanLocal")
    private AdminStaffSessionBeanLocal adminStaffSessionBeanLocal;

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            System.out.println("********* HERE 1");
            companySessionBeanLocal.retrieveCompanyByUsername("company0");
            System.out.println("********* HERE 2");
        } catch (CompanyNotFoundException ex) {
            System.out.println("********* HERE 3");
            initializeData();
        }
    }

    private void initializeData() {

        try {
            
            Company newCompany2 = new Company();
            newCompany2.setName("Company 2");
            newCompany2.setUsername("company2");
            newCompany2.setPassword("password");
            newCompany2.setEmail("company2@gmail.com");
            newCompany2.setDescription("This is Company2");
            newCompany2.setAvgRating(4.6);
            newCompany2.setIsVerified(true);
            newCompany2.setIsSuspended(false);
            newCompany2.setAccessRightEnum(AccessRightEnum.COMPANY);
            companySessionBeanLocal.createNewCompany(newCompany2);

            
            AdminStaff newAdmin2 = new AdminStaff() ; 
            newAdmin2.setName("admin lai");
            newAdmin2.setUsername("admin2");
            newAdmin2.setPassword("password");
            newAdmin2.setEmail("admin2@gmail.com");
            newAdmin2.setAccessRightEnum(AccessRightEnum.ADMIN);
            adminStaffSessionBeanLocal.createNewAdminStaff(newAdmin2);
            
        } catch (UserEmailExistsException | UnknownPersistenceException | InputDataValidationException | CompanyNameExistException ex) {
            ex.printStackTrace();

        }
    }

}
