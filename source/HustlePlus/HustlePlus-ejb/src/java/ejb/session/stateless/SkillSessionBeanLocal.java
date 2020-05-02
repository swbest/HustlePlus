/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Skill;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteSkillException;
import util.exception.InputDataValidationException;
import util.exception.SkillNameExistsException;
import util.exception.SkillNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateSkillException;

/**
 *
 * @author dtjldamien
 */
@Local
public interface SkillSessionBeanLocal {

    public Long createNewSkill(Skill newSkill) throws UnknownPersistenceException, InputDataValidationException, SkillNameExistsException;

    public Skill retrieveSkillBySkillId(Long skillId) throws SkillNotFoundException;

    public void updateSkill(Skill skill) throws SkillNotFoundException, UpdateSkillException, InputDataValidationException;

    public void deleteSkill(Long skillId) throws SkillNotFoundException, DeleteSkillException;

    public Skill retrieveSkillsBySkillTitle(String skillTitle) throws SkillNotFoundException;

    public List<Skill> retrieveAllSkills();

    public List<Skill> retrieveSkillsByStudentId(Long studentId);

    public Long studentAddSkill(Skill newSkill, Long studentId) throws UnknownPersistenceException, InputDataValidationException, SkillNameExistsException;
    
}
