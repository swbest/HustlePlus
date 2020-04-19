import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Team } from './team';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}

@Injectable({
	providedIn: 'root'
})

export class TeamService {

	baseUrl: string;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) 
	{
		this.baseUrl = this.sessionService.getRootPath() + 'Team';
	}

	createNewTeam(newTeam: Team): Observable<any> {
		let createNewTeamReq = { 'newTeam': newTeam };
		return this.httpClient.put<any>(this.baseUrl, createNewTeamReq, httpOptions).pipe
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

