import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {StudentService} from '../student.service';
import {Router} from '@angular/router';
import { NgForm } from '@angular/forms';
import {SessionService} from '../session.service';
import {Student} from '../student';
import { AccessRightEnum } from '../access-right-enum.enum';

@Component({
    selector: 'app-login',
    templateUrl: './login.page.html',
    styleUrls: ['./login.page.scss'],
})

export class LoginPage implements OnInit {

    submitted: boolean
    username: string;
    password: string;
    loginError: boolean;
    errorMessage: string;

    constructor(private studentService: StudentService,
                private router: Router,
                public sessionService: SessionService,) {
        this.submitted = false;
    }

    ngOnInit() {
    }

    clear() {
      this.username = "";
      this.password = "";
    }

    studentLogin(studentLoginForm: NgForm)
    {
      this.submitted = true;
      
      if (studentLoginForm.valid) 
      {
        this.sessionService.setUsername(this.username);
        this.sessionService.setPassword(this.password);
              
        this.studentService.studentLogin(this.username, this.password).subscribe(
          response => {										
            let student: Student = response.studentEntity;	
            
            if(response.studentEntity.accessRightEnum == 'STUDENT')
            {
              student.accessRightEnum = AccessRightEnum.STUDENT;
            }
            
            if(student != null)
            {
              this.sessionService.setIsLogin(true);
              this.sessionService.setCurrentStudent(student);					
              this.loginError = false;					
            }
            else
            {
              this.loginError = true;
            }
          },
          error => {
            this.loginError = true;
            this.errorMessage = error
          }
        );
      }
      else
      {
      }
    }
    
    
    
    studentLogout(): void
    {
      this.sessionService.setIsLogin(false);
      this.sessionService.setCurrentStudent(null);		
    }
    
    
    
    back()
    {
      this.router.navigate(["/home"]);
    }
}
