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
export class PaymentService {

	baseUrl: string;
	studentId: number;

	constructor(private httpClient: HttpClient,
		private sessionService: SessionService) {
		this.baseUrl = this.sessionService.getRootPath() + 'Payment';
	}

	getMyPaymentsByProjectId(projectId: number): Observable<any> {
		this.studentId = this.sessionService.getCurrentStudent().userId;
		return this.httpClient.get<any>(this.baseUrl + "/retrievePaymentsByProjectIdAndStudentId/" + projectId + "/" + this.studentId).pipe
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
