/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Company;
import entity.Project;
import java.util.List;
import javax.ejb.Local;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyNotVerifiedException;
import util.exception.CompanySuspendedException;
import util.exception.DeleteProjectException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNameExistException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author sw_be
 */
@Local
public interface ProjectSessionBeanLocal {

    public Project createNewProject(Project newProject, Long companyId) throws CompanyNotVerifiedException, CompanySuspendedException, UnknownPersistenceException, InputDataValidationException, ProjectNameExistException, CompanyNotFoundException;

    public List<Project> retrieveAllProject();

    public Project retrieveProjectByProjectId(Long projectId) throws ProjectNotFoundException;

    public void updateProject(Project project, Long companyId) throws CompanyNotFoundException, ProjectNotFoundException, UpdateProjectException, InputDataValidationException;

    public void deleteProject(Long projectId) throws ProjectNotFoundException, DeleteProjectException;

    public List<Project> retrieveProjectsByName(String pname);

    public List<Project> retrieveProjectsByCompany(String cname);

    public List<Project> retrieveProjectsBySkills(String skill);
    
}
