import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CompanyService } from '../company.service';
import { Company } from '../company';

@Component({
  selector: 'app-view-all-companies',
  templateUrl: './view-all-companies.page.html',
  styleUrls: ['./view-all-companies.page.scss'],
})

export class ViewAllCompaniesPage implements OnInit {

  companies: Company[];
  errorMessage: string;

  constructor(private router: Router, private companyService: CompanyService) { }

  ngOnInit() {
    this.companyService.getAllCompanies().subscribe(
      response => {
        this.companies = response.companies
      },
      error => {
        this.errorMessage = error
      }
    );
  }

  ionViewWillEnter() {
    this.refreshCompanies();
  }

  viewCompanyDetails(event, company) {
    this.router.navigate(["/viewCompanyDetails/" + company.userId]);
  }

  refreshCompanies() {
    this.companyService.getAllCompanies().subscribe(
      response => {
        this.companies = response.companies
      },
      error => {
        this.errorMessage = error
        console.log('********** ViewAllCompaniesPage.ts: ' + error);
      }
    );
  }
}
