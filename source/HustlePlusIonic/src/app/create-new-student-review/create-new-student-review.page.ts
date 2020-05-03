import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';

import { SessionService } from '../session.service';
import { StudentReviewService } from '../student-review.service';
import { StudentReview } from '../student-review';
import { StudentService } from '../student.service';
import { Student } from '../student';
import { ProjectService } from '../project.service';
import { Project } from '../project';
import { Colors } from '../colors';

@Component({
  selector: 'app-create-new-student-review',
  templateUrl: './create-new-student-review.page.html',
  styleUrls: ['./create-new-student-review.page.scss'],
})
export class CreateNewStudentReviewPage implements OnInit {

  submitted: boolean;
  newStudentReview: StudentReview;
  infoMessage: string;
  errorMessage: string;
  hasError: boolean;
  students: Student[];
  projects: Project[];
  ratingRange: number[] = [1, 2, 3, 4, 5];
  projectId: string;
  studentId: string;

  @Input() rating: number;
  @Output() ratingChange: EventEmitter<number> = new EventEmitter();

  constructor(private studentReviewService: StudentReviewService,
    private router: Router,
    private studentService: StudentService,
    private projectService: ProjectService,
    private sessionService: SessionService,
    private toastController: ToastController) {
    this.submitted = false;
    this.newStudentReview = new StudentReview();
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
    this.newStudentReview = new StudentReview();
  }

  create(createStudentReviewForm: NgForm) {
    this.submitted = true;

    if (createStudentReviewForm.valid) {
      this.newStudentReview.username = this.sessionService.getUsername();
      this.newStudentReview.rating = this.rating;
      this.studentReviewService.createNewStudentReview(this.newStudentReview, parseInt(this.projectId), parseInt(this.studentId)).subscribe(
        response => {
          this.infoMessage = 'New student review created ' + response.newStudentReviewId;
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
      this.router.navigate(["/reviews"]);
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

  async reviewToast() {
    const toast = await this.toastController.create({
      message: 'Successfully left a review for student id: ' + this.studentId,
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

