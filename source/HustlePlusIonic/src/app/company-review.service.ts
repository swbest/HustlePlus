import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { CompanyReview } from './company-review';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
	providedIn: 'root'
})
export class CompanyReviewService {

	baseUrl: string;

	constructor(private httpClient: HttpClient,
		private utilityService: UtilityService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Review';
	}

	createCompanyNewReview(newCompanyReview: CompanyReview): Observable<any> {
		let createNewCompanyReviewReq = { 'newReview': newCompanyReview };

		return this.httpClient.put<any>(this.baseUrl, createNewCompanyReviewReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getAllCompanyReviews(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllCompanyReviews").pipe
			(
				catchError(this.handleError)
			);
	}
	
	getCompanyReviewByCompanyReviewId(companyReviewId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveCompanyReview/" + companyReviewId).pipe
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
