import { Component, OnInit } from '@angular/core';
import { StudentService } from '../student.service';
import { SkillService } from '../skill.service';
import { SessionService } from '../session.service';
import { ModalController, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Student } from '../student';
import { Skill } from '../skill';
import { CreateNewSkillPage } from '../create-new-skill/create-new-skill.page';

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
  resultError: boolean;
  error: boolean;
  message: string;

  constructor(private studentService: StudentService,
    private skillService: SkillService,
    private sessionService: SessionService,
    private modalController: ModalController,
    private toastController: ToastController,
    private location: Location,
    private router: Router) {
    this.skills = new Array();
  }

  ngOnInit() {
    this.studentToView = this.sessionService.getCurrentStudent();
    console.log(this.studentToView);
    this.refreshSkills();
    this.studentInitialiseFields();
    error => {
      this.infoMessage = null;
      this.errorMessage = "Error retrieving student details.";
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
    this.resume = this.studentToView.resume;
    this.skills = this.sessionService.getSkills();
  }

  refreshSkills() {
    this.skillService.getSkillsByStudentId(this.studentToView.userId).subscribe(
      response => {
        this.skills = response.skills;
        this.sessionService.setSkills(response.skills);
      },
    )
  }

  dissociateSkillFromStudent(skillId) {
    this.skills.splice(skillId, 1);
    this.studentService.dissociateSkillFromStudent(this.studentToView, skillId).subscribe(
      response => {
        this.resultSuccess = true;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
    console.log("Skill successfully deleted");
    this.presentToast();
    this.refreshSkills();
    this.studentInitialiseFields();
  }

  deleteStudentAccount() {
    this.studentService.deleteStudentAccount(this.studentToView.userId).subscribe(
      response => {
        this.resultSuccess = true;
        this.deleteToast();
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
    this.router.navigate(["/updateProfileModal"]);
  }

  uploadResume() {
    this.router.navigate(["/resume"]);
  }

  async presentToast() {
    const toast = await this.toastController.create({
      message: 'Skill successfully removed from your profile!',
      duration: 2000
    });
    toast.present();
  }

  async createNewSkillModal() {
    const modal = await this.modalController.create({
      component: CreateNewSkillPage,
    });
    modal.onDidDismiss().then(data => {
      this.refreshSkills();
    });
    return await modal.present();
  }

  async deleteToast() {
    const toast = await this.toastController.create({
      message: 'Successfully deleted your Hustle+ account!',
      duration: 2000
    });
    toast.present();
  }
}



