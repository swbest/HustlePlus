import { Component, OnInit } from '@angular/core';

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

	constructor(private studentService: StudentService) { }

	ngOnInit() {
		this.studentService.getAllStudents().subscribe(
			response => {
				this.studentService = response.students
			},
			error => {
				this.errorMessage = error
			}
		);
	}
}
