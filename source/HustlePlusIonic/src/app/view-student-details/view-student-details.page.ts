import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController, ToastController } from '@ionic/angular';

import { TeamService } from '../team.service';
import { SkillService } from '../skill.service';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { Skill } from '../skill';
import { Team } from '../team';

@Component({
  selector: 'app-view-student-details',
  templateUrl: './view-student-details.page.html',
  styleUrls: ['./view-student-details.page.scss'],
})
export class ViewStudentDetailsPage implements OnInit {

  userId: number;
  studentToView: Student;
  skills: Skill[];
  teams: Team[];
  retrieveStudentError: boolean;
  retreieveSkillsError: boolean;
  retrieveTeamsError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;
  submitted: boolean;
  infoMessage: string;
  hasError: boolean;
  teamId: number;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService,
    private skillService: SkillService,
    private teamService: TeamService,
    public alertController: AlertController,
    private toastController: ToastController) {
    this.retrieveStudentError = false;
    this.retreieveSkillsError = false;
    this.retrieveTeamsError = false;
    this.error = false;
    this.resultSuccess = false;
    this.submitted = false;
    this.hasError = false;
  }

  ngOnInit() {
    this.userId = parseInt(this.activatedRoute.snapshot.paramMap.get('userId'));
    this.refreshStudent();
  }

  ionViewWillEnter() {
    this.refreshStudent();
  }

  refreshStudent() {
    this.studentService.getStudentByStudentId(this.userId).subscribe(
      response => {
        this.studentToView = response.student;
      },
      error => {
        this.retrieveStudentError = true;
        console.log('********** ViewStudentDetailsPage.ts: ' + error);
      }
    );
    this.skillService.getSkillsByStudentId(this.userId).subscribe(
      response => {
        this.skills = response.skills;
      },
      error => {
        this.retreieveSkillsError = true;
        console.log('********** ViewStudentDetailsPage.ts: ' + error);
      }
    );
    this.teamService.getMyTeams().subscribe(
      response => {
        this.teams = response.teams;
      },
      error => {
        this.retrieveTeamsError = true;
        console.log('********** ViewStudentDetailsPage.ts: ' + error);
      }
    );
  }

  addToTeam(addToTeamForm: NgForm) {
    this.submitted = true;
    this.teamService.addStudentToTeam(this.teamId, this.studentToView.userId).subscribe(
      response => {
        this.infoMessage = 'Added student:' + this.studentToView.name + ' to team id: ' + this.teamId;
        this.errorMessage = null;
        this.hasError = false;
        this.successToast();
      },
      error => {
        this.infoMessage = null;
        this.errorMessage = error;
        this.hasError = true;
        this.failToast();
      }
    );
    this.back();
  }

  back() {
    this.router.navigate(["/teams"]);
  }

  async successToast() {
		const toast = await this.toastController.create({
			message: this.infoMessage,
			duration: 3000
		});
		toast.present();
	}

  async failToast() {
		const toast = await this.toastController.create({
			message: this.errorMessage,
			duration: 3000
		});
		toast.present();
	}
}
