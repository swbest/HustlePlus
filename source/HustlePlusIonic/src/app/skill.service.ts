import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { Skill } from './skill';
import { SessionService } from './session.service';

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
		private utilityService: UtilityService,
		private sessionService: SessionService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Skill';
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

  createNewSkill(newSkill: Skill): Observable<any> {
    let createNewSkillReq = { 
      'newSkill': newSkill
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
