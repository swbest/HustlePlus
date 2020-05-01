import { ModalController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { StudentService } from '../student.service';
import { SessionService } from '../session.service';
import { Location } from '@angular/common';
import { Student } from '../student';
import { AccessRightEnum } from '../access-right-enum.enum';

@Component({
  selector: 'app-update-profile-modal',
  templateUrl: './update-profile-modal.page.html',
  styleUrls: ['./update-profile-modal.page.scss'],
})
export class UpdateProfileModalPage implements OnInit {

  userId: number;
  studentToUpdate: Student;
  name: string;
  username: string;
  password: string;
  email: string;
  description: string;
  bankAccountName: string;
  bankAccountNumber: number;
  resume: File;
  errorMessage: string;
  infoMessage: string;
  retrieveStudentError: boolean;
  resultSuccess: boolean;
  resultError: boolean;
  submitted: boolean;
  message: string;

  constructor(private studentService: StudentService,
    private sessionService: SessionService,
    private modalController: ModalController,
    private toastController: ToastController,
    private location: Location,
    private router: Router) {

  }

  ngOnInit() {
    this.studentToUpdate = this.sessionService.getCurrentStudent();
    this.studentInitialiseFields();
    error => {
      this.infoMessage = null;
      this.errorMessage = "Error retrieving student details.";
      this.errorToast();
      this.location.back();
    }
  }

  studentInitialiseFields() {
    this.userId = this.studentToUpdate.userId;
    this.name = this.studentToUpdate.name;
    this.username = this.studentToUpdate.username;
    this.password = this.studentToUpdate.password;
    this.email = this.studentToUpdate.email;
    this.description = this.studentToUpdate.description;
    this.bankAccountName = this.studentToUpdate.bankAccountName;
    this.bankAccountNumber = this.studentToUpdate.bankAccountNumber;
    this.resume = this.studentToUpdate.resume;
  }

  updateStudentAccount(updateStudentForm: NgForm) {
    this.submitted = true;

    if (updateStudentForm.valid) {
      this.studentToUpdate.accessRightEnum = "STUDENT";

      this.studentService.updateStudent(this.studentToUpdate).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "Account updated successfully";
          this.sessionService.setCurrentStudent(this.studentToUpdate);
          this.sessionService.setUsername(this.studentToUpdate.username);
          this.successToast();
          this.back();
        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occurred while updating the account: " + error;
          this.errorToast();
          console.log('********** UpdateAccountComponent.ts: ' + error);
        }
      );
      this.studentService.updatePassword(this.studentToUpdate.password).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.message = "Password updated successfully";
          this.sessionService.setPassword(this.studentToUpdate.password);      
          this.successToast();
          this.back();
        },
        error => {
          this.resultError = true;
          this.resultSuccess = false;
          this.message = "An error has occurred while updating password: " + error;
          this.errorToast();
          console.log('********** UpdateAccountComponent.ts: ' + error);
        }
      )
    }
  }

  async successToast() {
    const toast = await this.toastController.create({
      message: "Successfully updated your profile details!",
      duration: 3000
    });

    toast.present();

  }

  async errorToast() {
    const toast = await this.toastController.create({
      message: this.message,
      duration: 3000
    });

    toast.present();

  }

  closeModal() {
    this.modalController.dismiss();
  }

  back() {
    this.router.navigate(["/profile"]);
  }

}
