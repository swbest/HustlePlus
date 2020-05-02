import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController, ToastController } from '@ionic/angular';

import { ProjectService } from '../project.service';
import { Project } from '../project';
import { SessionService } from '../session.service';
import { Student } from '../student';
import { Application } from '../application';
import { ApplicationService } from '../application.service';

@Component({
  selector: 'app-view-project-details',
  templateUrl: './view-project-details.page.html',
  styleUrls: ['./view-project-details.page.scss'],
})
export class ViewProjectDetailsPage implements OnInit {

  projectId: number;
  projectToView: Project;
  retrieveProjectError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;
  students: Student[];
  studentPartOfProject: boolean;
  newApplication: Application;
  infoMessage: string;
  hasError: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    public alertController: AlertController,
    private sessionService: SessionService,
    private applicationService: ApplicationService,
    private toastController: ToastController) {
    this.retrieveProjectError = false;
    this.error = false;
    this.resultSuccess = false;
    this.newApplication = new Application();
  }

  ngOnInit() {
    this.projectId = parseInt(this.activatedRoute.snapshot.paramMap.get('projectId'));
    this.refreshProject();
  }

  ionViewWillEnter() {
    this.refreshProject();
  }

  refreshProject() {
    this.projectService.getProjectByProjectId(this.projectId).subscribe(
      response => {
        this.projectToView = response.project;
      },
      error => {
        this.retrieveProjectError = true;
        console.log('********** ViewProjectDetailsPage.ts: ' + error);
      }
    );
    /* this.students = this.projectToView.students;
    for (let student of this.students) {
      if (student.userId = this.sessionService.getCurrentStudent().userId) {
        this.studentPartOfProject = true;
      }
    }
    */
  }

  apply() {
    this.applicationService.createNewApplication(this.newApplication, this.projectId, this.sessionService.getCurrentStudent().userId).subscribe(
      response => {
        this.infoMessage = 'New application created ' + response.newApplicationId;
        this.errorMessage = null;
        this.hasError = true;
        this.applyProjectToast();
        this.back();
      },
      error => {
        this.infoMessage = null;
        this.errorMessage = error;
        this.hasError = false;
        this.failToast();
      }
    );
  }

  startDateTime() {
    const string = this.projectToView.startDate.toString();
    return string.substring(0, 10);
  }

  endDateTime() {
    const string = this.projectToView.endDate.toString();
    return string.substring(0, 10);
  }

  async applyProjectToast() {
    const toast = await this.toastController.create({
      message: 'Successfully applied for project ' + this.projectToView.projectName,
      duration: 2000
    });
    toast.present();
  }  

  back() {
    this.router.navigate(["/projects"]);
  }

  async failToast() {
		const toast = await this.toastController.create({
			message: this.errorMessage,
			duration: 3000
		});
		toast.present();
	}
}
