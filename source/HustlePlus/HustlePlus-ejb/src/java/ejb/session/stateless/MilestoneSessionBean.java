/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Milestone;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hiixdayah
 */
@Stateless
public class MilestoneSessionBean implements MilestoneSessionBeanLocal {

    @PersistenceContext(unitName = "HustlePlus-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewMilestone(Milestone newMilestone) {
        em.persist(newMilestone);
        em.flush();
        return newMilestone.getMilestoneId();
    }

    @Override
    public Milestone retrieveMilestoneByMilestoneId(Long milestoneId) { // throws MilestoneNotFoundException
        Milestone milestone = em.find(Milestone.class, milestoneId);

        if (milestone != null) {
            return milestone;
        }

        return milestone ;
        //else {
          //  throw new MilestoneNotFoundException("Milestone ID " + milestoneId + " does not exist!");
        }

    @Override
    public void updateMilestone(Milestone milestone) {
        em.merge(milestone);
    }

    @Override
    public void deleteMilestone(Long milestoneId)  { //throws MilestoneNotFoundException
        Milestone milestoneToRemove = retrieveMilestoneByMilestoneId(milestoneId);
        em.remove(milestoneToRemove);
    }


    @Override
    public List<Milestone> retrieveAllMilestones() {
        Query query = em.createQuery("SELECT m FROM Milestone m");

        return query.getResultList();

    }

}

