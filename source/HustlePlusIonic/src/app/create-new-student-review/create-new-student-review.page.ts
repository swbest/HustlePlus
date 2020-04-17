import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { ReviewService } from '../review.service';
import { Review } from '../review';

@Component({
  selector: 'app-create-new-student-review',
  templateUrl: './create-new-student-review.page.html',
  styleUrls: ['./create-new-student-review.page.scss'],
})
export class CreateNewStudentReviewPage implements OnInit {

  submitted: boolean;
  newReview: Review;
  infoMessage: string;
  errorMessage: string;

  constructor(private reviewService: ReviewService) {
    this.submitted = false;
    this.newReview = new Review();
  }

  ngOnInit() {
  }

  clear() {
    this.submitted = false;
    this.newReview = new Review();
  }

  create(createReviewForm: NgForm) {
    this.submitted = true;

    if (createReviewForm.valid) {
      this.reviewService.createNewReview(this.newReview).subscribe(
        response => {
          this.infoMessage = 'New student created ' + response.newBookId;
          this.errorMessage = null;
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
        }
      );
    }
  }

  redirectHome() {

  }
}

