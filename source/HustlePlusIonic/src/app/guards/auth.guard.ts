import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, CanLoad, Route, UrlSegment, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';



import { SessionService } from '../session.service';
import { Student } from '../student';
import { AccessRightEnum } from '../access-right-enum.enum';



@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate, CanActivateChild, CanLoad
{	
	constructor(private router: Router,
				private sessionService: SessionService)
	{		
	}
	
	
	
	canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree 
	{
		console.log('********** AuthGuard.canActivate(): ' + next.url[0] + ',' + next.url[1]);
		
		if(this.sessionService.getIsLogin())
		{
			student: Student;
			let student = this.sessionService.getCurrentStudent();			
			
			//console.log(student.accessRightEnum);

			if(student.accessRightEnum == "STUDENT")
			{
				return true;		
			}

			this.router.navigate(['/accessRightError']);
			return false;
		}
		else
		{
			this.router.navigate(['/accessRightError']);
			return false;
		}
	}
	
	canActivateChild(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree
	{
		return true;
	}
	
	canLoad(route: Route, segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean
	{
		return true;
	}
}
