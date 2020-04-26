import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController } from '@ionic/angular';

import { ProjectService } from '../project.service';
import { Project } from '../project';

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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private projectService: ProjectService,
    public alertController: AlertController) {
    this.retrieveProjectError = false;
    this.error = false;
    this.resultSuccess = false;
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
  }

  back() {
    this.router.navigate(["/viewAllProjects"]);
  }
}
