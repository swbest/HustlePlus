/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

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
import util.exception.SkillNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProjectException;

/**
 *
 * @author sw_be
 */
@Local
public interface ProjectSessionBeanLocal {

    public Long createNewProject(Project newProject, Long companyId, List<Long> skillIds) throws SkillNotFoundException, CompanyNotVerifiedException, CompanySuspendedException, UnknownPersistenceException, InputDataValidationException, ProjectNameExistException, CompanyNotFoundException;

    public List<Project> retrieveAllProjects();

    public Project retrieveProjectByProjectId(Long projectId) throws ProjectNotFoundException;

    public void updateProject(Project project, List<Long> skillIds) throws SkillNotFoundException, ProjectNotFoundException, UpdateProjectException, InputDataValidationException;

    public void deleteProject(Long projectId) throws ProjectNotFoundException, DeleteProjectException;

    public List<Project> retrieveProjectsByName(String pname) throws ProjectNotFoundException;

    public List<Project> retrieveProjectsByCompany(Long cid)throws ProjectNotFoundException;

    public List<Project> retrieveProjectsBySkills(String skill) throws ProjectNotFoundException;

    public List<Project> filterProjectsBySkills(List<Long> skillIds, String condition);

    public List<Project> searchProjectsByName(String searchString);

    public List<Project> filterProjectByCompanies(List<Long> companyIds);

    public List<Project> searchProjectsByCompany(String searchString);

    public void disassociateProjectSkill(Long projectId, Long skillId) throws ProjectNotFoundException, SkillNotFoundException;

    public List<Project> retrieveProjectsByStudentId(Long studentId) throws ProjectNotFoundException;
    
}
