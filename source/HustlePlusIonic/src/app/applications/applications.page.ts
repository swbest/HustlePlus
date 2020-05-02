import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ToastController } from '@ionic/angular';

import { Application } from '../application';
import { ApplicationService } from '../application.service';

@Component({
  selector: 'app-applications',
  templateUrl: './applications.page.html',
  styleUrls: ['./applications.page.scss'],
})
export class ApplicationsPage implements OnInit {

  applications: Application[];
  errorMessage: string;
  error: boolean;
  numApplications: number;

  constructor(private router: Router,
    private applicationService: ApplicationService,
    private toastController: ToastController) { }

  ngOnInit() {
    this.refreshApplications();
  }

  ionViewWillEnter() {
    this.refreshApplications();
  }

  refreshApplications() {
    console.log("**** getting my applications")
    this.applicationService.getMyApplications().subscribe(
      response => {
        this.applications = response.applications
        this.numApplications = this.applications.length;
      },
      error => {
        this.errorMessage = error
        console.log('********** ViewAllApplicationsPage.ts: ' + error);
      }
    );
  }

  getSearchApplications(ev: any) {
    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
      this.applications = this.applications.filter((application) => {
        return (application.project.projectName.toLowerCase().indexOf(val.toLowerCase()) > -1);
      })
    }
  }

  deleteApplication(event, application) {
    this.applicationService.deleteApplication(application.applicationId).subscribe(
      response => {
        this.successDeleteToast(application);
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        this.failDeleteToast();
      }
    );
    this.refreshApplications();
  }

  onCancel(ev: any) {
    // Reset items back to all of the items
    this.refreshApplications();
  }

  applyForProjects() {
    this.router.navigate(["/projects"]);
  }

  async successDeleteToast(application) {
		const toast = await this.toastController.create({
			message: 'Successfully deleted application for project: ' + application.project.projectName,
			duration: 3000
		});
		toast.present();
  }
  
  async failDeleteToast() {
		const toast = await this.toastController.create({
			message: this.errorMessage,
			duration: 3000
		});
		toast.present();
	}
}
