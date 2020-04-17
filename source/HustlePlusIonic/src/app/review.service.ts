import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { Review } from './review';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
	providedIn: 'root'
})
export class ReviewService {

	baseUrl: string;

	constructor(private httpClient: HttpClient,
		private utilityService: UtilityService) {
		this.baseUrl = this.utilityService.getRootPath() + 'Review';
	}

	createNewReview(newReview: Review): Observable<any> {
		let createNewReviewReq = { 'newReview': newReview };

		return this.httpClient.put<any>(this.baseUrl, createNewReviewReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	getAllReviews(): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveAllReviews").pipe
			(
				catchError(this.handleError)
			);
	}
	
	getReviewByReviewId(reviewId: number): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveReview/" + reviewId).pipe
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
