import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController, ToastController } from '@ionic/angular';

import { TeamService } from '../team.service';
import { Team } from '../team';

@Component({
  selector: 'app-view-team-details',
  templateUrl: './view-team-details.page.html',
  styleUrls: ['./view-team-details.page.scss'],
})
export class ViewTeamDetailsPage implements OnInit {

  teamId: number;
  teamToView: Team;
  retrieveTeamError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;
  studentId: number;
  removeStudentMsg: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private teamService: TeamService,
    public alertController: AlertController,
    private toastController: ToastController) {
    this.retrieveTeamError = false;
    this.error = false;
    this.resultSuccess = false;
  }

  ngOnInit() {
    this.teamId = parseInt(this.activatedRoute.snapshot.paramMap.get('teamId'));
    this.refreshTeam();
  }

  ionViewWillEnter() {
    this.refreshTeam();
  }

  refreshTeam() {
    this.teamService.getTeamByTeamId(this.teamId).subscribe(
      response => {
        this.teamToView = response.team;
      },
      error => {
        this.retrieveTeamError = true;
        console.log('********** ViewTeamDetailsPage.ts: ' + error);
      }
    );
  }

  deleteTeam() {
    this.teamService.deleteTeam(this.teamId).subscribe(
      error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
    this.back();
  }

  removeStudent(event, student) {
    if (this.teamToView.numStudents == 1) {
      this.removeStudentMsg = "You need at least one student in the team!";
      this.atLeastOneToast();
    } else {
      this.teamService.removeStudent(this.teamId, student.userId).subscribe(
        response => {
          this.successToast();
        },
        error => {
          this.error = true;
          this.errorMessage = error;
          this.failToast();
        }
      );
    }
    this.refreshTeam();
  }

  back() {
    this.router.navigate(["/teams"]);
  }

  async successToast() {
		const toast = await this.toastController.create({
			message: 'Successfully removed student: ' + this.studentId + ' from team!',
			duration: 3000
		});
		toast.present();
  }
  
  async failToast() {
		const toast = await this.toastController.create({
			message: this.removeStudentMsg,
			duration: 3000
		});
		toast.present();
	}

  async atLeastOneToast() {
		const toast = await this.toastController.create({
			message: this.removeStudentMsg,
			duration: 3000
		});
		toast.present();
	}
}
