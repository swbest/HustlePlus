/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Application;
import java.util.List;
import javax.ejb.Local;
import util.exception.ApplicationExistException;
import util.exception.ApplicationNotFoundException;
import util.exception.DeleteApplicationException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
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

    public List<Application> retrieveAllApplicationss();

    public Application retrieveApplicationByApplicationId(Long applicationId) throws ApplicationNotFoundException;

    public Long createApplication(Application newApplication, Long projectId, Long studentId) throws StudentSuspendedException, StudentNotVerifiedException, ApplicationExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException, StudentNotFoundException;

    public void updateApplication(Application application) throws ApplicationNotFoundException, UpdateApplicationException, InputDataValidationException;

    public void deleteApplication(Long applicationId) throws ApplicationNotFoundException, DeleteApplicationException;
    
}
