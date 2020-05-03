import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { SessionService } from '../session.service';
import { StudentReviewService } from '../student-review.service';
import { CompanyReviewService } from '../company-review.service';
import { CompanyReview } from '../company-review';
import { StudentReview } from '../student-review';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.page.html',
  styleUrls: ['./reviews.page.scss'],
})
export class ReviewsPage implements OnInit {

  studentReviews: StudentReview[];
  companyReviews: CompanyReview[];
  errorMessage: string;

  constructor(private studentReviewService: StudentReviewService,
    private companyReviewService: CompanyReviewService,
    private router: Router) {
  }

  ngOnInit() {
    this.refreshReviews();
  }

  ionViewWillEnter() {
    this.refreshReviews();
  }

  refreshReviews() {
    this.studentReviewService.getMyReviewsFromStudents().subscribe(
      response => {
        this.studentReviews = response.studentReviews
      },
      error => {
        this.errorMessage = error
      }
    );
    this.studentReviewService.getMyReviewsFromCompanies().subscribe(
      response => {
        this.companyReviews = response.studentReviews
      },
      error => {
        this.errorMessage = error
      }
    );
  }

  writeStudentReview() {
    this.router.navigate(["/createNewStudentReview"])
  }

  writeCompanyReview() {
    this.router.navigate(["/createNewCompanyReview"])
  }

  back() {
    this.router.navigate(["/home"]);
  }
}
