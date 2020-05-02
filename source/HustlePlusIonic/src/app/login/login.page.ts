import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { Skill } from '../skill';

import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})

export class LoginPage implements OnInit {

  student: Student;
  skills: Skill[];
  submitted: boolean
  username: string;
  password: string;
  loginError: boolean;
  errorMessage: string;

  constructor(private studentService: StudentService,
    private router: Router,
    public sessionService: SessionService,
    private toastController: ToastController) {
    this.submitted = false;
  }

  ngOnInit() {
  }

  clear() {
    this.username = "";
    this.password = "";
  }

  studentLogin(studentLoginForm: NgForm) {
    this.submitted = true;

    if (studentLoginForm.valid) {
      this.sessionService.setUsername(this.username);
      this.sessionService.setPassword(this.password);

      this.studentService.studentLogin(this.username, this.password).subscribe(
        response => {
          let student: Student = response.student;

          student.accessRightEnum = "STUDENT";
          console.log(response);

          if (student != null) {
            this.student = student;
            this.sessionService.setIsLogin(true);
            this.sessionService.setCurrentStudent(student);
            this.loginError = false;
            this.loginToast();
            this.back();
          }
          else {
            this.loginError = true;
          }
        },
        error => {
          this.loginError = true;
          this.errorMessage = error
        }
      );
    }
    else {
    }
  }

  studentLogout(): void {
    this.sessionService.setIsLogin(false);
    this.sessionService.setCurrentStudent(null);
    console.log('successful logout');
    this.logoutToast();
    this.logout();
  }

  back() {
    this.router.navigate(["/home"]);
  }

  register() {
    this.router.navigate(["/createNewStudent"]);
  }  

  logout() {
    this.router.navigate(["/login"]);
  }

  async loginToast() {
    const toast = await this.toastController.create({
      message: 'Welcome to Hustle+',
      duration: 3000
    });
    toast.present();
  }

  async logoutToast() {
    const toast = await this.toastController.create({
      message: 'You have logged out of Hustle+',
      duration: 3000
    });
    toast.present();
  }

  
}