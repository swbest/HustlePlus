/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Team;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteTeamException;
import util.exception.InputDataValidationException;
import util.exception.ProjectNotFoundException;
import util.exception.TeamNameExistException;
import util.exception.TeamNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateTeamException;

/**
 *
 * @author amanda
 */
@Local
public interface TeamSessionBeanLocal {

    public Long createNewTeam(Team newTeam, Long projectId) throws TeamNameExistException, UnknownPersistenceException, InputDataValidationException, ProjectNotFoundException;

    public List<Team> retrieveAllTeams();

    public Team retrieveTeamByTeamId(Long teamId) throws TeamNotFoundException;

    public void updateTeam(Team team, Long projectId, List<Long> studentIds) throws TeamNotFoundException, /*ProjectNotFoundException, StudentNotFoundException,*/ UpdateTeamException, InputDataValidationException;

    public void deleteTeam(Long teamId) throws TeamNotFoundException, DeleteTeamException;
}
