import { Component, OnInit, Input, EventEmitter ,Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { ReviewService } from '../review.service';
import { Review } from '../review';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { ProjectService } from '../project.service';
import { Project } from '../project';
import { Colors } from './colors';

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
  hasError: boolean;
  students: Student[];
  projects: Project[];
  ratingRange: number[] = [1, 2, 3, 4, 5];

  @Input() rating: number;
  @Output() ratingChange: EventEmitter<number> = new EventEmitter();

  constructor(private reviewService: ReviewService,
    private router: Router,
    private studentService: StudentService,
    private projectService: ProjectService) {
    this.submitted = false;
    this.newReview = new Review();
      
  }

  ngOnInit() {
    this.refreshStudents();
    this.refreshProjects();
  }

  ionViewWillEnter() {
    this.refreshStudents();
    this.refreshProjects();
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
          this.infoMessage = 'New student created ' + response.newReviewId;
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

