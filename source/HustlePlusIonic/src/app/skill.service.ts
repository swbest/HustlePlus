import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';

import { Skill } from './skill';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
	providedIn: 'root'
})
export class SkillService {

	baseUrl: string;
	studentId: number;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {
		this.baseUrl = this.sessionService.getRootPath() + 'Skill';
	}

	getSkills(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllSkills").pipe
			(
				catchError(this.handleError)
			);
	}

	getSkillsByStudentId(studentId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveSkillsByStudentId/" + studentId).pipe
			(
				catchError(this.handleError)
			);
	}

	getMySkills(): Observable<any> {
		this.studentId = this.sessionService.getCurrentStudent().userId;
		return this.httpClient.get<any>(this.baseUrl + "/retrieveSkillsByStudentId/" + this.studentId).pipe
			(
				catchError(this.handleError)
			);
	}

	deleteSkill(skillId: number): Observable<any> {
		return this.httpClient.delete<any>(this.baseUrl + "/deleteSkill/" + skillId).pipe
			(
				catchError(this.handleError)
			);
	}

	createNewSkill(newSkill: Skill): Observable<any> {
		this.studentId = this.sessionService.getCurrentStudent().userId;
		let createNewSkillReq = {
			'newSkill': newSkill,
			'studentId': this.studentId
		};

		return this.httpClient.put<any>(this.baseUrl, createNewSkillReq, httpOptions).pipe
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
