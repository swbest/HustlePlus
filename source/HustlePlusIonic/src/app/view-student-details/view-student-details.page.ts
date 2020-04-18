import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AlertController } from '@ionic/angular';

import { StudentService } from '../student.service';
import { Student } from '../student';

@Component({
  selector: 'app-view-student-details',
  templateUrl: './view-student-details.page.html',
  styleUrls: ['./view-student-details.page.scss'],
})
export class ViewStudentDetailsPage implements OnInit {

  userId: number;
  studentToView: Student;
  retrieveStudentError: boolean;
  error: boolean;
  errorMessage: string;
  resultSuccess: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private studentService: StudentService,
    public alertController: AlertController) {
    this.retrieveStudentError = false;
    this.error = false;
    this.resultSuccess = false;
  }

  ngOnInit() {
    this.userId = parseInt(this.activatedRoute.snapshot.paramMap.get('userId'));
    this.refreshStudent();
  }

  ionViewWillEnter() {
    this.refreshStudent();
  }

  refreshStudent() {
    this.studentService.getStudentByStudentId(this.userId).subscribe(
      response => {
        this.studentToView = response.student;
      },
      error => {
        this.retrieveStudentError = true;
        console.log('********** ViewStudentDetailsPage.ts: ' + error);
      }
    );
  }

  back() {
    this.router.navigate(["/viewAllStudents"]);
  }
}
