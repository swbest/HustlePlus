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
	searchQuery: string = '';

	constructor(private router: Router, private studentService: StudentService) { }

	ngOnInit() {
		this.refreshStudents();
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

	getSearchStudents(ev: any) {
		// Reset items back to all of the items
		//		this.refreshStudents();

		// set val to the value of the searchbar
		const val = ev.target.value;

		// if the value is an empty string don't filter the items
		if (val && val.trim() != '') {
			this.students = this.students.filter((student) => {
				return (student.name.toLowerCase().indexOf(val.toLowerCase()) > -1);
				// || student.skills.filter(skill => skill.title.toLowerCase().indexOf(val.toLowerCase()) == 0));
			})
		}
	}

	onCancel(ev: any) {
		// Reset items back to all of the items
		this.refreshStudents();
	}
}
