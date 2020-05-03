import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { CompanyReview } from './company-review';
import { SessionService } from './session.service';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
	providedIn: 'root'
})
export class CompanyReviewService {

	baseUrl: string;
	studentId: number;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {
		this.baseUrl = this.sessionService.getRootPath() + 'CompanyReview';
	}

	createNewCompanyReview(newCompanyReview: CompanyReview, projectId: number, companyId: number, studentId: number): Observable<any> {
		let createNewCompanyReviewReq = {
			'newCompanyReview': newCompanyReview,
			'projectId': projectId,
			'companyId': companyId,
			'studentId': studentId
		};
		return this.httpClient.put<any>(this.baseUrl, createNewCompanyReviewReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getMyCompanyReviews(): Observable<any> {
		this.studentId = this.sessionService.getCurrentStudent().userId;
		return this.httpClient.get<any>(this.baseUrl + "/retrieveMyCompanyReviews/" + this.studentId).pipe
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
