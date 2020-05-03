import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController, ToastController } from '@ionic/angular';

import { ProjectService } from '../project.service';
import { Project } from '../project';
import { Student } from '../student';
import { Application } from '../application';
import { Milestone } from '../milestone';
import { MilestoneService } from '../milestone.service';
import { Payment } from '../payment';
import { PaymentService } from '../payment.service';

@Component({
  selector: 'app-view-my-project-details',
  templateUrl: './view-my-project-details.page.html',
  styleUrls: ['./view-my-project-details.page.scss'],
})
export class ViewMyProjectDetailsPage implements OnInit {

  projectId: number;
  projectToView: Project;
  retrieveProjectError: boolean;
  retrieveMilestoneError: boolean;
  retrievePaymentError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;
  students: Student[];
  studentPartOfProject: boolean;
  newApplication: Application;
  infoMessage: string;
  hasError: boolean;
  milestones: Milestone[];
  payments: Payment[];

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    private milestoneService: MilestoneService,
    private paymentService: PaymentService,
    public alertController: AlertController) {
    this.retrieveProjectError = false;
    this.error = false;
    this.resultSuccess = false;
    this.newApplication = new Application();
  }

  ngOnInit() {
    this.projectId = parseInt(this.activatedRoute.snapshot.paramMap.get('projectId'));
    this.refreshProject();
    this.refreshMilestones();
    this.refreshPayments();
  }

  ionViewWillEnter() {
    this.refreshProject();
    this.refreshMilestones();
    this.refreshPayments();
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
  }

  refreshMilestones() {
    this.milestoneService.getMilestonesByProjectId(this.projectId).subscribe(
      response => {
        this.milestones = response.milestones;
        console.log(this.milestones);
      },
      error => {
        this.retrieveMilestoneError = true;
        console.log('********** ViewProjectDetailsPage.ts: ' + error);
      }
    );
  }

  refreshPayments() {
    this.paymentService.getMyPaymentsByProjectId(this.projectId).subscribe(
      response => {
        this.payments = response.payments;
        console.log(this.payments);
      },
      error => {
        this.retrievePaymentError = true;
        console.log('********** ViewProjectDetailsPage.ts: ' + error);
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

  back() {
    this.router.navigate(["/projects"]);
  }
}
