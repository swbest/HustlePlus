import { Component, OnInit } from '@angular/core';

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

  constructor(private companyService: CompanyService) { }

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
}
