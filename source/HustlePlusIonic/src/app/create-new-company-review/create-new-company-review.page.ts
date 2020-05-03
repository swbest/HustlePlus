import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';

import { SessionService } from '../session.service';
import { CompanyReviewService } from '../company-review.service';
import { CompanyReview } from '../company-review';
import { CompanyService } from '../company.service';
import { Company } from '../company';
import { ProjectService } from '../project.service';
import { Project } from '../project';
import { Colors } from '../colors';

@Component({
  selector: 'app-create-new-company-review',
  templateUrl: './create-new-company-review.page.html',
  styleUrls: ['./create-new-company-review.page.scss'],
})
export class CreateNewCompanyReviewPage implements OnInit {

  submitted: boolean;
  newCompanyReview: CompanyReview;
  infoMessage: string;
  errorMessage: string;
  hasError: boolean;
  companies: Company[];
  projects: Project[];
  ratingRange: number[] = [1, 2, 3, 4, 5];
  projectId: string;
  companyId: string;
  studentId: number;

  @Input() rating: number;
  @Output() ratingChange: EventEmitter<number> = new EventEmitter();

  constructor(private companyReviewService: CompanyReviewService,
    private router: Router,
    private companyService: CompanyService,
    private projectService: ProjectService,
    private sessionService: SessionService,
    private toastController: ToastController) {
    this.submitted = false;
    this.newCompanyReview = new CompanyReview();
  }

  ngOnInit() {
    this.refreshCompanies();
    this.refreshProjects();
  }

  ionViewWillEnter() {
    this.refreshCompanies();
    this.refreshProjects();
	}

  clear() {
    this.submitted = false;
    this.newCompanyReview = new CompanyReview();
  }

  create(createCompanyReviewForm: NgForm) {
    this.submitted = true;

    if (createCompanyReviewForm.valid) {
      this.newCompanyReview.rating = this.rating;
      this.studentId = this.sessionService.getCurrentStudent().userId;
      this.companyReviewService.createNewCompanyReview(this.newCompanyReview, parseInt(this.projectId), parseInt(this.companyId), this.studentId).subscribe(
        response => {
          this.infoMessage = 'New company review created ' + response.newCompanyReviewId;
          this.errorMessage = null;
          this.hasError = true;
          this.reviewToast();
          this.back();
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
          this.hasError = false;
          this.failToast();
        }
      );
    }
  }

	refreshCompanies() {
		this.companyService.getAllCompanies().subscribe(
			response => {
				this.companies = response.companies
			},
			error => {
				this.errorMessage = error
			}
		);
	}

	refreshProjects() {
		this.projectService.getProjects().subscribe(
			response => {
				this.projects = response.projects
			},
			error => {
				this.errorMessage = error
			}
		);
	}

  rate(index: number) {
    this.rating = index;
    this.ratingChange.emit(this.rating);
  }

  isAboveRating(index: number): boolean {
    return index > this.rating;
  }

  getColor(index: number) {
    if (this.isAboveRating(index)) {
      return Colors.GREY;
    }
    switch (this.rating) {
      case 1:
      case 2:
        return Colors.RED;
      case 3:
        return Colors.YELLOW;
      case 4:
      case 5:
        return Colors.GREEN;
      default:
        return Colors.GREY;
    }
  }

  back() {
      this.router.navigate(["/reviews"]);
  }

  async reviewToast() {
    const toast = await this.toastController.create({
      message: 'Successfully left a review for company id: ' + this.companyId,
      duration: 2000
    });
    toast.present();
  }

  async failToast() {
    const toast = await this.toastController.create({
      message: this.errorMessage,
      duration: 2000
    });
    toast.present();
  }
}

