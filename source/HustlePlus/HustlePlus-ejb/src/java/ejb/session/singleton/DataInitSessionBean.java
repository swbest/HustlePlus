/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AdminStaffSessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.SkillSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.AdminStaff;
import entity.Company;
import entity.Project;
import entity.Skill;
import entity.Student;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.SkillNameExistsException;
import util.exception.StudentNameExistException;
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

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @EJB(name = "StudentSessionBeanLocal")
    private StudentSessionBeanLocal studentSessionBeanLocal;

    @EJB(name = "SkillSessionBeanLocal")
    private SkillSessionBeanLocal skillSessionBeanLocal;

    @EJB(name = "AdminStaffSessionBeanLocal")
    private AdminStaffSessionBeanLocal adminStaffSessionBeanLocal;

    @EJB(name = "CompanySessionBeanLocal")
    private CompanySessionBeanLocal companySessionBeanLocal;

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            System.out.println("********* HERE 1");
            companySessionBeanLocal.retrieveCompanyByUsername("company1");
            System.out.println("********* HERE 2");
        } catch (CompanyNotFoundException ex) {
            System.out.println("********* initializeData()");
            initializeData();
        }
    }

    private void createCompany() {
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
            newCompany.setIsVerified(true);
            newCompany.setIsSuspended(false);
        } catch (CompanyNameExistException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createAdmin() {
        try {
            AdminStaff newAdmin = new AdminStaff();
            newAdmin.setName("admin tan");
            newAdmin.setUsername("admin1");
            newAdmin.setPassword("password");
            newAdmin.setEmail("admin1@gmail.com");
            newAdmin.setAccessRightEnum(AccessRightEnum.ADMIN);
            adminStaffSessionBeanLocal.createNewAdminStaff(newAdmin);
        } catch (UserEmailExistsException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSkills() {
        try {
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
            newSkill5.setTitle("Angular");
            skillSessionBeanLocal.createNewSkill(newSkill5);
            Skill newSkill6 = new Skill();
            newSkill6.setTitle("React");
            skillSessionBeanLocal.createNewSkill(newSkill6);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SkillNameExistsException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createStudents() {
        try {
            Student newStudent = new Student();
            newStudent.setBankAccountName("dbs");
            newStudent.setBankAccountNumber(Long.valueOf("12345678"));
            newStudent.setName("student one");
            newStudent.setUsername("studentone");
            newStudent.setPassword("password");
            newStudent.setEmail("studentone@gmail.com");
            newStudent.setDescription("information systems undergraduate");
            studentSessionBeanLocal.createStudentAccount(newStudent);
        } catch (StudentNameExistException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createProjects() {
        try {
            Project newProject = new Project();
            newProject.setProjectName("IS3106 Hustle+");
            newProject.setJobValue(BigDecimal.valueOf(310.60));
            newProject.setNumStudentsRequired(4);
            newProject.setProjectDescription("Enterprise Systems");
            newProject.setStartDate(new Date());
            newProject.setEndDate(new Date());
            projectSessionBeanLocal.createNewProject(newProject, Long.valueOf("1"));
        } catch (CompanyNotFoundException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CompanyNotVerifiedException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CompanySuspendedException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownPersistenceException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InputDataValidationException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProjectNameExistException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeData() {
        createCompany();
        createAdmin();
        createSkills();
        createStudents();
        // createProjects();
    }
}
