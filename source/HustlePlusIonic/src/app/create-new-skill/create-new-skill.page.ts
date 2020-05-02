import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { ToastController } from '@ionic/angular';

import { SessionService } from '../session.service';
import { StudentService } from '../student.service';
import { SkillService } from '../skill.service';
import { Student } from '../student';
import { Skill } from '../skill';


@Component({
  selector: 'app-create-new-skill',
  templateUrl: './create-new-skill.page.html',
  styleUrls: ['./create-new-skill.page.scss'],
})
export class CreateNewSkillPage implements OnInit {

  studentToAddSkill: Student;
  newSkill: Skill;
  submitted: boolean;
	infoMessage: string;
	errorMessage: string;
  hasError: boolean;
  resultSuccess: boolean;
  resultError: boolean;
  error: boolean;

  constructor(private modalController: ModalController,
    private sessionService: SessionService,
    private studentService: StudentService,
    private skillService: SkillService,
    private toastController: ToastController) {
      this.submitted = false;
      this.newSkill = new Skill();
    }

  ngOnInit() {
    this.studentToAddSkill = this.sessionService.getCurrentStudent();
    console.log(this.studentToAddSkill);
  }

  createNewSkill(addSkillForm: NgForm) {
		this.submitted = true;

		if (addSkillForm.valid) {
			this.skillService.createNewSkill(this.newSkill).subscribe(
				response => {
          this.infoMessage = 'Skill ID: ' + this.newSkill.title + ' added!';
          this.newSkill.skillId = response.newSkillId;
          console.log(response.newSkillId);
					this.errorMessage = null;
          this.hasError = true;
          this.addSkillToast();
				},
				error => {
					this.infoMessage = null;
					this.errorMessage = error;
					this.hasError = false;
				}
      );
    }
	}

  /*
  ionViewDidLeave() {
    this.studentService.addSkillToStudent(this.studentToAddSkill, this.newSkill.skillId).subscribe(
      response => {
        this.resultSuccess = true;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
      }
    );	
  }
  */

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  async addSkillToast() {
    const toast = await this.toastController.create({
      message: 'Created new skill: ' + this.newSkill.title,
      duration: 2000
    });
    toast.present();
  }  
}
