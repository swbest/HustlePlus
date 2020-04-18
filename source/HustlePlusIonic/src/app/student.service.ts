import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Student } from './student';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
	providedIn: 'root'
})

export class StudentService {

	baseUrl: string;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) 
	{
		this.baseUrl = this.sessionService.getRootPath() + 'Student';
	}

	studentLogin(username: string, password: string): Observable<any>
	{
		return this.httpClient.get<any>(this.baseUrl + "/studentLogin?username=" + username + "&password=" + password).pipe
		(
			catchError(this.handleError)
		);
	}

	createNewStudent(newStudent: Student): Observable<any> {
		let createNewStudentReq = { 'newStudent': newStudent };

		return this.httpClient.put<any>(this.baseUrl, createNewStudentReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getAllStudents(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllStudents").pipe
			(
				catchError(this.handleError)
			);
	}
	
	getStudentByStudentId(userId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveStudent/" + userId).pipe
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
