import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { UtilityService } from './utility.service';
import { Application } from './application';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  baseUrl: string;
  studentId: number;

  constructor(private httpClient: HttpClient,
    private utilityService: UtilityService,
    private sessionService: SessionService) {
    this.baseUrl = this.utilityService.getRootPath() + 'Application';
  }

  createNewApplication(newApplication: Application, projectId: number, studentId: number): Observable<any> {
    let createNewApplicationReq = {
      'newApplication': newApplication,
      'projectId': projectId,
      'studentId': studentId
    };

    return this.httpClient.put<any>(this.baseUrl, createNewApplicationReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );
  }

  getMyApplications(): Observable<any> {
    this.studentId = this.sessionService.getCurrentStudent().userId;
    return this.httpClient.get<any>(this.baseUrl + "/retrieveApplicationsByStudentId/" + this.studentId).pipe
      (
        catchError(this.handleError)
      );
  }

  getApplicationByApplicationid(applicationId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveApplication/" + applicationId).pipe
      (
        catchError(this.handleError)
      );
  }

	deleteApplication(applicationId: number): Observable<any> {
    console.log("deleteing application with id: " + applicationId);
		return this.httpClient.delete<any>(this.baseUrl + "/deleteApplication/" + applicationId).pipe
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
