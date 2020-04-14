/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminStaffSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.SkillSessionBeanLocal;
import entity.AdminStaff;
import entity.Company;
import entity.Skill;
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
import util.exception.SkillNameExistsException;
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

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

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
            companySessionBeanLocal.retrieveCompanyByUsername("company1");
            System.out.println("********* HERE 2");
        } catch (CompanyNotFoundException ex) {
            System.out.println("********* HERE 3");
            initializeData();
        }
    }

    private void initializeData() {

        try {
            
            Company newCompany = new Company();
            newCompany.setName("Company 1");
            newCompany.setUsername("company1");
            newCompany.setPassword("password");
            newCompany.setEmail("company1@gmail.com");
            newCompany.setDescription("This is Company1");
            newCompany.setAvgRating(5.0);
            newCompany.setIsVerified(true);
            newCompany.setIsSuspended(false);
            newCompany.setAccessRightEnum(AccessRightEnum.COMPANY);
            companySessionBeanLocal.createNewCompany(newCompany); 

        AdminStaff newAdmin = new AdminStaff() ; 
        newAdmin.setName("admin tan");
        newAdmin.setUsername("admin1");
        newAdmin.setPassword("password");
        newAdmin.setEmail("admin1@gmail.com");
        newAdmin.setAccessRightEnum(AccessRightEnum.ADMIN);
        adminStaffSessionBeanLocal.createNewAdminStaff(newAdmin);
        
        Skill newSkill1 = new Skill(); 
        newSkill1.setTitle("Java");
        skillSessionBeanLocal.createNewSkill(newSkill1);
        Skill newSkill2 = new Skill(); 
        newSkill2.setTitle("Python");
        skillSessionBeanLocal.createNewSkill(newSkill2);
        Skill newSkill3 = new Skill(); 
        newSkill3.setTitle("C++");
        skillSessionBeanLocal.createNewSkill(newSkill3);
        Skill newSkill4 = new Skill(); 
        newSkill4.setTitle("MySQL");
        skillSessionBeanLocal.createNewSkill(newSkill4);
        Skill newSkill5 = new Skill(); 
        newSkill5.setTitle("JSF");
        skillSessionBeanLocal.createNewSkill(newSkill5);
        Skill newSkill6 = new Skill(); 
        newSkill6.setTitle("React");
        skillSessionBeanLocal.createNewSkill(newSkill6);
        
        } catch (SkillNameExistsException | CompanyNameExistException | UserEmailExistsException | UnknownPersistenceException | InputDataValidationException ex) {
            ex.printStackTrace();

        }
    }

}
