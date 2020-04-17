import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { Student } from './student';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
	providedIn: 'root'
})
export class StudentService {

	baseUrl: string;
	viewAllStudentsUrl: string;

	constructor(private httpClient: HttpClient,
		private utilityService: UtilityService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Student';
	}

	createNewStudent(newStudent: Student): Observable<any> {
		let createNewStudentReq = { 'newStudent': newStudent };

		return this.httpClient.put<any>(this.baseUrl, createNewStudentReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getAllStudents(): Observable<any> {
		this.viewAllStudentsUrl = this.baseUrl + '/viewAllStudents'
		return this.httpClient.get<any>(this.viewAllStudentsUrl).pipe
			(
				catchError(this.handleError)
			);
	}

	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = '';

		if (error.error instanceof ErrorEvent) {
			errorMessage = 'An unknown error has occurred: ' + error.error.message;
		}
		else {
			errorMessage = 'An HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
		}

		return throwError(errorMessage)
	}
}
