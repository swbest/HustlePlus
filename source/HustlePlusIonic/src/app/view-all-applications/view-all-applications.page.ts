import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Application } from '../application';
import { ApplicationService } from '../application.service';

@Component({
  selector: 'app-view-all-applications',
  templateUrl: './view-all-applications.page.html',
  styleUrls: ['./view-all-applications.page.scss'],
})
export class ViewAllApplicationsPage implements OnInit {

  applications: Application[];
  errorMessage: string;
  error: boolean;
  numApplications: number;

  constructor(private router: Router,
    private applicationService: ApplicationService) { }

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
      error => {
        this.error = true;
        this.errorMessage = error;
      }
    );
    this.refreshApplications();
  }

  onCancel(ev: any) {
    // Reset items back to all of the items
    this.refreshApplications();
  }

  applyForProjects() {
    this.router.navigate(["/viewAllProjects"]);
  }
}
