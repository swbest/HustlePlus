import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { UtilityService } from './utility.service';
import { StudentReview } from './student-review';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}


@Injectable({
  providedIn: 'root'
})
export class StudentReviewService {

  baseUrl: string;

  constructor(private httpClient: HttpClient,
    private utilityService: UtilityService) {
    this.baseUrl = this.utilityService.getRootPath() + 'StudentReview';
  }

  createNewStudentReview(newStudentReview: StudentReview, projectId: number, studentId: number): Observable<any> {
    let createNewStudentReviewReq = { 
      'newStudentReview': newStudentReview,
      'projectId': projectId,
      'studentId': studentId
    };

    return this.httpClient.put<any>(this.baseUrl, createNewStudentReviewReq, httpOptions).pipe
      (
        catchError(this.handleError)
      );
  }

  getAllStudentReviews(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllStudentReviews").pipe
      (
        catchError(this.handleError)
      );
  }

  getStudentReviewByStudentReviewId(studentReviewId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveStudentReview/" + studentReviewId).pipe
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
