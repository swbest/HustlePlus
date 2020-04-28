import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { SessionService } from '../session.service';
import { StudentReviewService } from '../student-review.service';
import { StudentReview } from '../student-review';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { CompanyReviewService } from '../company-review.service';
import { CompanyReview } from '../company-review';
import { CompanyService } from '../company.service';
import { Company } from '../company';
import { ProjectService } from '../project.service';
import { Project } from '../project';
import { Colors } from '../colors';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.page.html',
  styleUrls: ['./reviews.page.scss'],
})
export class ReviewsPage implements OnInit {

  submitted: boolean;
  newStudentReview: StudentReview;
  newCompanyReview: CompanyReview;
  infoMessage: string;
  errorMessage: string;
  hasError: boolean;
  students: Student[];
  companies: Company[];
  projects: Project[];
  ratingRange: number[] = [1, 2, 3, 4, 5];
  projectId: string;
  companyId: string;
  studentId: number;

  @Input() rating: number;
  @Output() ratingChange: EventEmitter<number> = new EventEmitter();

  constructor(private studentReviewService: StudentReviewService,
    private companyReviewService: CompanyReviewService,
    private router: Router,
    private studentService: StudentService,
    private companyService: CompanyService,
    private projectService: ProjectService,
    private sessionService: SessionService) {
    this.submitted = false;
    this.newStudentReview = new StudentReview();
    this.newCompanyReview = new CompanyReview();
    this.studentId = this.sessionService.getCurrentStudent().userId;
  }

  ngOnInit() {
    this.refreshStudents();
    this.refreshCompanies();
    this.refreshProjects();
  }

  ionViewWillEnter() {
    this.refreshStudents();
    this.refreshCompanies();
    this.refreshProjects();
  }

  clear() {
    this.submitted = false;
    this.newStudentReview = new StudentReview();
    this.newCompanyReview = new CompanyReview();
  }

  createStudentReview(createStudentReviewForm: NgForm) {
    this.submitted = true;

    if (createStudentReviewForm.valid) {
      this.newStudentReview.username = this.sessionService.getUsername();
      this.newStudentReview.rating = this.rating;
      this.studentReviewService.createNewStudentReview(this.newStudentReview, parseInt(this.projectId), this.studentId).subscribe(
        response => {
          this.infoMessage = 'New student review created ' + response.newStudentReviewId;
          this.errorMessage = null;
          this.hasError = true;
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
          this.hasError = false;
        }
      );
    }
  }

  createCompanyReview(createCompanyReviewForm: NgForm) {
    this.submitted = true;

    if (createCompanyReviewForm.valid) {
      this.newCompanyReview.rating = this.rating;
      this.companyReviewService.createNewCompanyReview(this.newCompanyReview, parseInt(this.projectId), parseInt(this.companyId), this.studentId).subscribe(
        response => {
          this.infoMessage = 'New company review created ' + response.newCompanyReviewId;
          this.errorMessage = null;
          this.hasError = true;
        },
        error => {
          this.infoMessage = null;
          this.errorMessage = error;
          this.hasError = false;
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

  refreshStudents() {
    this.studentService.getAllStudents().subscribe(
      response => {
        this.students = response.students
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

  back() {
    if (!this.hasError) {
      this.router.navigate(["/home"]);
    }
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
}
