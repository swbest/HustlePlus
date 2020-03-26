/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.SkillSessionBeanLocal;
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

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal; 
    
    public DataInitSessionBean() {
    }

     
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            System.out.println("********* HERE 1");
            companySessionBeanLocal.retrieveCompanyByUsername("company1");
            System.out.println("********* HERE 2");
        }
        catch(CompanyNotFoundException ex)
        {
            System.out.println("********* HERE 3");
            initializeData();
        }
    }
    
    private void initializeData() {
        
        try { 
        
        Company newCompany = new Company() ; 
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
        
        skillSessionBeanLocal.createNewSkill(new Skill("skill1")) ; 
        
        
    } catch (SkillNameExistsException | CompanyNameExistException | UnknownPersistenceException | InputDataValidationException ex) {
        ex.printStackTrace();

    }
}
    
}
