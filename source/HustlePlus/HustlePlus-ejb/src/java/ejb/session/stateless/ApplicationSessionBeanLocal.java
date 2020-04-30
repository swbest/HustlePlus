/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Application;
import entity.Student;
import java.util.List;
import javax.ejb.Local;
import util.exception.ApplicationExistException;
import util.exception.ApplicationNotFoundException;
import util.exception.ApproveApplicationException;
import util.exception.DeleteApplicationException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.StudentAppliedToProjectException;
import util.exception.StudentAssignedToProjectException;
import util.exception.StudentNotFoundException;
import util.exception.StudentNotVerifiedException;
import util.exception.StudentSuspendedException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateApplicationException;

/**
 *
 * @author dtjldamien
 */
@Local
public interface ApplicationSessionBeanLocal {

    public List<Application> retrieveAllApplications();

    public Application retrieveApplicationByApplicationId(Long applicationId) throws ApplicationNotFoundException;

    public Long createApplication(Application newApplication, Long projectId, Long studentId) throws StudentAppliedToProjectException, StudentAssignedToProjectException, StudentSuspendedException, StudentNotVerifiedException, ApplicationExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException, StudentNotFoundException;

    public void updateApplication(Application application) throws ApplicationNotFoundException, UpdateApplicationException, InputDataValidationException;

    public void deleteApplication(Long applicationId) throws ApplicationNotFoundException, DeleteApplicationException;

    public List<Application> retrieveApplicationByProject(Long projectId);

    public void approveApplication(Long appId) throws ApproveApplicationException, ApplicationNotFoundException;

    public void rejectApplication(Long appId) throws ApproveApplicationException, ApplicationNotFoundException;

    public List<Student> retrieveStudentByApprovedApplication(Long projectId) throws ApplicationNotFoundException;

    public List<Application> retrieveApplicationByStudent(Long studentId);

}
