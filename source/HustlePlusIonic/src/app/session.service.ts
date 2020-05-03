import { Injectable } from '@angular/core';
import { Platform } from '@ionic/angular'
import { Skill } from './skill';
import { Student } from './student'
import { AccessRightEnum } from './access-right-enum.enum';

@Injectable({
  providedIn: 'root'
})

export class SessionService {

  constructor(private platform: Platform) { }

  getRootPath(): string {
    console.log('this.platform.is("hybrid"): ' + this.platform.is('hybrid'));

    if (this.platform.is('hybrid')) {
      // change ip address here
      return "http://192.168.68.134:8080/HustlePlusRws/Resources/";
    }
    else {
      return "/api/";
    }
  }

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
    sessionStorage.currentStudent = JSON.stringify(currentStudent);
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

  checkAccessRight(path): boolean {
    console.log("********** path: " + path);

    if (this.getIsLogin()) {
      student: Student;
      let student = this.getCurrentStudent();

      if (student.accessRightEnum == "STUDENT") {
        if (path == "/viewAllStudents" ||
          path == "/viewAllCompanies"
        ) {
          return true;
        }
        else {
          return false;
        }
      }

      return false;
    }
    else {
      return false;
    }
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
