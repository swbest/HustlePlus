import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

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

	constructor(private studentService: StudentService) {
		this.submitted = false;
		this.newStudent = new Student();
	}

	ngOnInit() {
	}
	
	clear() {
	this.submitted = false;
	this.newStudent = new Student();
	}

	create(createStudentForm: NgForm)
	{
		this.submitted = true;
		
		if(createStudentForm.valid) {
			this.studentService.createNewStudent(this.newStudent).subscribe(
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
}

