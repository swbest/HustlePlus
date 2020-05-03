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
export class CompanyService {

  baseUrl: string;

  constructor(private httpClient: HttpClient,
    private sessionService: SessionService) {
    this.baseUrl = this.sessionService.getRootPath() + 'Company';
  }

  getAllCompanies(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllCompanies").pipe
      (
        catchError(this.handleError)
      );
  }

  getCompanyByCompanyId(userId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveCompany/" + userId).pipe
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
