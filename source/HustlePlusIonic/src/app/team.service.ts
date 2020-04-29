import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Team } from './team';
import { UtilityService } from './utility.service';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
	providedIn: 'root'
})

export class TeamService {

	baseUrl: string;
	studentId: number;

	constructor(private httpClient: HttpClient,
		private utilityService: UtilityService,
		private sessionService: SessionService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Team';
		this.studentId = this.sessionService.getCurrentStudent().userId;
	}

	createNewTeam(newTeam: Team): Observable<any> {
		let createNewTeamReq = { 
			'newTeam': newTeam,
			'studentId': this.studentId,
		};
		return this.httpClient.put<any>(this.baseUrl + "/createNewTeam", createNewTeamReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getTeamByTeamId(teamId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveTeam/" + teamId).pipe
			(
				catchError(this.handleError)
			);
	}

	getTeamByStudentId(studentId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveTeamsByStudentId/" + studentId).pipe
			(
				catchError(this.handleError)
			);
	}

	getMyTeams(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveTeamsByStudentId/" + this.studentId).pipe
			(
				catchError(this.handleError)
			);
	}

	addStudentToTeam(teamId: number, studentToAddId: number): Observable<any> {
		let updateTeamReq = { 
			'teamId': teamId,
			'studentId': studentToAddId,
		};
		return this.httpClient.put<any>(this.baseUrl + "/addStudentToTeam", updateTeamReq, httpOptions).pipe
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

