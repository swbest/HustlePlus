/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.MilestoneIdExistException;
import util.exception.MilestoneNotFoundException;
import util.exception.ProjectNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateMilestoneException;

/**
 *
 * @author hiixdayah
 */
@Local
public interface MilestoneSessionBeanLocal {

    public Milestone createNewMilestone(Milestone newMilestone, Long projectId) throws MilestoneIdExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException;

    public Milestone retrieveMilestoneByMilestoneId(Long milestoneId) throws MilestoneNotFoundException;

    public void updateMilestone(Milestone milestone) throws MilestoneNotFoundException, UpdateMilestoneException, InputDataValidationException;

    public void deleteMilestone(Long milestoneId) throws MilestoneNotFoundException ;

    public List<Milestone> retrieveAllMilestones();
    
}

