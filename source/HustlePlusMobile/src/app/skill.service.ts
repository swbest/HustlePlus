import { Injectable } from '@angular/core';
import { SessionService } from './session.service';
import { Skill } from './skill';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  skills: Skill[];

  constructor(private sessionService: SessionService) {
    this.skills = this.sessionService.getSkills();

    if (this.skills == null) {
      this.skills = new Array();
      let skill: Skill;

      skill = new Skill(1, "Java");
      this.skills.push(skill);

      skill = new Skill(2, "JavaScript");
      this.skills.push(skill);

      skill = new Skill(2, "JavaScript");
      this.skills.push(skill);

      skill = new Skill(3, "Python");
      this.skills.push(skill);

      skill = new Skill(4, "C");
      this.skills.push(skill);

      skill = new Skill(5, "C++");
      this.skills.push(skill);

      skill = new Skill(6, "C#");
      this.skills.push(skill);

      this.sessionService.setSkills(this.skills);
    }
  }

  getSkills() {
    return this.skills;
  }

  createNewSkill(newSkill: Skill) {
    let skill: Skill = new Skill();
    skill.skillId = newSkill.skillId;
    skill.title = newSkill.title;

    this.skills.push(skill);

    this.sessionService.setSkills(this.skills);
  }
}
