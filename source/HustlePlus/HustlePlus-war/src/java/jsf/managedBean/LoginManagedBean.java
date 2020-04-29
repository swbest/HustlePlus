/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.ProjectSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Company;
import entity.Project;
import entity.User;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import util.enumeration.AccessRightEnum;
import util.exception.InvalidLoginCredentialException;
import util.exception.ProjectNotFoundException;

/**
 *
 * @author Nurhidayah
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

    @EJB(name = "ProjectSessionBeanLocal")
    private ProjectSessionBeanLocal projectSessionBeanLocal;

    @EJB
    private UserSessionBeanLocal userSessionBeanLocal;

    private String username;
    private String password;

    private Company companyToDisplayProjects;
    private List<Project> projectsToDisplay;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

    @PostConstruct
    public void PostConstruct() {
        setCompanyToDisplayProjects((Company) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("companyToDisplayProjects"));
        projectsToDisplay = (List<Project>) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("projectsToDisplay");
    }

    public void login(ActionEvent event) throws IOException {

        try {
            User userEntity = userSessionBeanLocal.login(username, password);
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userEntity", userEntity);

            if (userEntity.getAccessRightEnum() == AccessRightEnum.COMPANY) {
                companyToDisplayProjects = (Company) event.getComponent().getAttributes().get("companyToDisplayProjects");
                ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("companyToDisplayProjects", companyToDisplayProjects);
                projectsToDisplay = projectSessionBeanLocal.retrieveProjectsByCompany(companyToDisplayProjects.getUserId());
                ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("projectsToDisplay", projectsToDisplay);
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
            }

        } catch (InvalidLoginCredentialException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credentials: " + ex.getMessage(), null));
        } catch (ProjectNotFoundException | IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving projects: " + ex.getMessage(), null));
        }
    }

    public void logout(javax.faces.event.ActionEvent event) throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Company getCompanyToDisplayProjects() {
        return companyToDisplayProjects;
    }

    public void setCompanyToDisplayProjects(Company companyToDisplayProjects) {
        this.companyToDisplayProjects = companyToDisplayProjects;
    }

    public List<Project> getProjectsToDisplay() {
        return projectsToDisplay;
    }

    public void setProjectsToDisplay(List<Project> projectsToDisplay) {
        this.projectsToDisplay = projectsToDisplay;
    }

}
