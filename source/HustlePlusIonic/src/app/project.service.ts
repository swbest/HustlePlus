import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { Project } from './project';


const httpOptions = {
	headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
  providedIn: 'root'
})

export class ProjectService {
	
	baseUrl: string;

	constructor(private httpClient: HttpClient,
				private utilityService: UtilityService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Project';
	}
	
	getProjects(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl).pipe
		(
			catchError(this.handleError)
		);
	}

	getProjectByProjectId(projectId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/" + projectId).pipe
			(
				catchError(this.handleError)
			);
	}

		
	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = '';
		
		if(error.error instanceof ErrorEvent) {
			errorMessage = 'An unknown error has occurred: ' + error.error.message;
		} else {
			errorMessage = 'An HTTP error has occurred: ' + `HTTP ${error.status}: ${error.error.message}`;
		}
		return throwError(errorMessage)
	}
}
