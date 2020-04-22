import { Component, OnInit } from '@angular/core';
import { StudentService } from '../student.service';
import { SessionService } from '../session.service';
import { ModalController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Student } from '../student';
import { Skill } from '../skill';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  userId: number;
  studentToView: Student;
  name: string;
  username: string;
  password: string;
  email: string;
  description: string;
  avgRating: number;
  isVerified: boolean;
  isSuspended: boolean;
  bankAccountName: string;
  bankAccountNumber: number;
  skills: Skill[];
  resume: File;
  errorMessage: string;
  infoMessage: string;
  resultSuccess: boolean;
  resultError: boolean
  error: boolean;
  message: string;

  constructor(private studentService: StudentService,
    private sessionService: SessionService,
    private modalController: ModalController,
    private toastController:ToastController,
    private location: Location,
    private router: Router
    ) { 
      this.skills = new Array();

    }

    ngOnInit() {
      this.studentToView = this.sessionService.getCurrentStudent();
      this.skills = this.sessionService.getSkills();     
      this.studentInitialiseFields();
        error => {
          this.infoMessage = null;
          this.errorMessage = "Error retrieving student details.";
          this.errorToast();
          this.location.back();
        }
    } 
  
  
    studentInitialiseFields() {
      this.userId = this.studentToView.userId;
      this.name = this.studentToView.name;
      this.username = this.studentToView.username;
      this.password = this.studentToView.password;
      this.email = this.studentToView.email;
      this.description = this.studentToView.description;
      this.avgRating = this.studentToView.avgRating;
      this.isVerified = this.studentToView.isVerified;
      this.isSuspended = this.studentToView.isSuspended;
      this.bankAccountName = this.studentToView.bankAccountName;
      this.bankAccountNumber = this.studentToView.bankAccountNumber;
      this.skills = this.studentToView.skills;
      this.resume = this.studentToView.resume;
    }

    deleteStudentAccount() {
      this.studentService.deleteStudentAccount(this.studentToView.userId).subscribe(
        response => {
          this.resultSuccess = true;
        },
        error => {
          this.error = true;
          this.errorMessage = error;
        }
      );	
      this.studentService.studentLogout();	
      this.router.navigate(["/home"]);
    }

    updateStudentAccount() {
      this.router.navigate(["/update-profile-modal"]);
    }

    async successToast() {
      const toast = await this.toastController.create({
        message: this.infoMessage,
        duration: 3000
      });
  
      toast.present();
      
      }
  
      async errorToast() {
      const toast = await this.toastController.create({
        message: this.errorMessage,
        duration: 3000
      });
  
      toast.present();
      
      }
  
      closeModal(){
        this.modalController.dismiss();
      } 
   
    
  }

  
  
