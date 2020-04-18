import { Injectable } from '@angular/core';
import { Skill } from './skill';
import { Student } from './student'

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() { }

  getIsLogin(): boolean {
    if (sessionStorage.isLogin == "true") {
      return true;
    }
    else {
      return false;
    }
  }

  setIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin;
  }

  getCurrentStudent(): Student {
    return JSON.parse(sessionStorage.currentStudent);
  }

  setCurrentStudent(currentStudent: Student): void {
    sessionStorage.currentStaff = JSON.stringify(currentStudent);
  }

  getUsername(): string {
    return sessionStorage.username;
  }

  setUsername(username: string): void {
    sessionStorage.username = username;
  }

  getPassword(): string {
    return sessionStorage.password;
  }

  setPassword(password: string): void {
    sessionStorage.password = password;
  }


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
