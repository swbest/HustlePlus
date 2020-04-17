import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { StudentService } from '../student.service';
import { Student } from '../student';

@Component({
	selector: 'app-view-all-students',
	templateUrl: './view-all-students.page.html',
	styleUrls: ['./view-all-students.page.scss'],
})

export class ViewAllStudentsPage implements OnInit {

	students: Student[];
	errorMessage: string;

	constructor(private router: Router, private studentService: StudentService) { }

	ngOnInit() {
		this.refreshStudents()
	}

	ionViewWillEnter() {
		this.refreshStudents();
	}

	viewStudentDetails(event, student) {
		this.router.navigate(["/viewStudentDetails/" + student.userId]);
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
}
