import { Injectable } from '@angular/core';
import { Skill } from './skill';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  getSkills(): Skill[] {
    try {
      return JSON.parse(sessionStorage.skills);
    } catch {
      return null;
    }
  }

  setSkills(skills: Skill[]): void {
    sessionStorage.skills = JSON.stringify(skills);
  }
}
