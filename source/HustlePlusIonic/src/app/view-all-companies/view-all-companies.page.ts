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
    this.refreshCompanies();
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

  getSearchCompanies(ev: any) {
    // Reset items back to all of the items
    //		this.refreshStudents();

    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
      this.companies = this.companies.filter((company) => {
        return (company.name.toLowerCase().indexOf(val.toLowerCase()) > -1);
      })
    }
  }

  onCancel(ev: any) {
    // Reset items back to all of the items
    this.refreshCompanies();
  }
}
