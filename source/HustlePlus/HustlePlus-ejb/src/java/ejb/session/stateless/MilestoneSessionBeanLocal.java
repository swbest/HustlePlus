/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hiixdayah
 */
@Local
public interface MilestoneSessionBeanLocal {

    public Long createNewMilestone(Milestone newMilestone);

    public Milestone retrieveMilestoneByMilestoneId(Long milestoneId);

    public void updateMilestone(Milestone milestone);

    public void deleteMilestone(Long milestoneId);

    public List<Milestone> retrieveAllMilestones();

}

