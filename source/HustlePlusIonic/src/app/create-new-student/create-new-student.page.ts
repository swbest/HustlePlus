import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { StudentService } from '../student.service';
import { Student } from '../student';

@Component({
	selector: 'app-create-new-student',
	templateUrl: './create-new-student.page.html',
	styleUrls: ['./create-new-student.page.scss'],
})
export class CreateNewStudentPage implements OnInit {

	submitted: boolean;
	newStudent: Student;
	infoMessage: string;
	errorMessage: string;
	hasError: boolean;

	constructor(private studentService: StudentService,
		private router: Router) {
		this.submitted = false;
		this.newStudent = new Student();
	}

	ngOnInit() {
	}

	clear() {
		this.submitted = false;
		this.newStudent = new Student();
	}

	create(createStudentForm: NgForm) {
		this.submitted = true;

		if (createStudentForm.valid) {
			this.studentService.createNewStudent(this.newStudent).subscribe(
				response => {
					this.infoMessage = 'Your account was succesfully created with ID: ' + response.newStudentId;
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

	back() {
		if (!this.hasError) {
			this.router.navigate(["/home"]);
		}
	}
}

