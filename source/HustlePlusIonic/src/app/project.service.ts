import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
	providedIn: 'root'
})

export class ProjectService {

	baseUrl: string;
	studentId: number;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {
		this.baseUrl = this.sessionService.getRootPath() + 'Project';
	}

	getProjects(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllProjects").pipe
			(
				catchError(this.handleError)
			);
	}

	getProjectByProjectId(projectId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveProject/" + projectId).pipe
			(
				catchError(this.handleError)
			);
	}

	getProjectsByStudentId(newStudentId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveProjectsByStudentId/" + newStudentId).pipe
			(
				catchError(this.handleError)
			);
	}

	getProjectsByCompanyId(companyId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveProjectsByCompanyId/" + companyId).pipe
			(
				catchError(this.handleError)
			);
	}

	getMyProjects(): Observable<any> {
		this.studentId = this.sessionService.getCurrentStudent().userId;
		return this.httpClient.get<any>(this.baseUrl + "/retrieveProjectsByStudentId/" + this.studentId).pipe
			(
				catchError(this.handleError)
			);
	}

	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = '';

		if (error.error instanceof ErrorEvent) {
			errorMessage = 'An unknown error has occurred: ' + error.error.message;
		} else {
			errorMessage = 'An HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
		}
		return throwError(errorMessage)
	}
}
