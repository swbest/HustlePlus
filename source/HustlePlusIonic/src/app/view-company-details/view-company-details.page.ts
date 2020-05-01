import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController } from '@ionic/angular';

import { EmailComposer } from '@ionic-native/email-composer/ngx';

import { ProjectService } from '../project.service';
import { Project } from '../project';
import { CompanyService } from '../company.service';
import { Company } from '../company';

@Component({
  selector: 'app-view-company-details',
  templateUrl: './view-company-details.page.html',
  styleUrls: ['./view-company-details.page.scss'],
})
export class ViewCompanyDetailsPage implements OnInit {

  userId: number;
  companyToView: Company;
  retrieveCompanyError: boolean;
  retrieveProjectsError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;
  projects: Project[]

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private companyService: CompanyService,
    public alertController: AlertController,
    private emailComposer: EmailComposer,
    private projectService: ProjectService) {
    this.retrieveCompanyError = false;
    this.retrieveProjectsError = false;
    this.error = false;
    this.resultSuccess = false;
  }

  ngOnInit() {
    this.userId = parseInt(this.activatedRoute.snapshot.paramMap.get('userId'));
    this.refreshCompany();
  }

  ionViewWillEnter() {
    this.refreshCompany();
  }

  refreshCompany() {
    this.companyService.getCompanyByCompanyId(this.userId).subscribe(
      response => {
        this.companyToView = response.company;
      },
      error => {
        this.retrieveCompanyError = true;
        console.log('********** ViewCompanyDetailsPage.ts: ' + error);
      }
    );
    this.projectService.getProjectsByCompanyId(this.userId).subscribe(
      response => {
        this.projects = response.projects;
      },
      error => {
        this.retrieveProjectsError = true;
        console.log('********** ViewCompanyDetailsPage.ts: ' + error);
      }
    )
  }

  back() {
    this.router.navigate(["/viewAllCompanies"]);
  }

  emailCompany() {
    this.emailComposer.open({
      to: this.companyToView.email,
      body: 'Sent from HustlePlus'
    })
  }
}
