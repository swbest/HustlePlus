import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },

  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then(m => m.HomePageModule)
  },

  { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
  { path: 'accessRightError', loadChildren: './access-right-error/access-right-error.module#AccessRightErrorPageModule' },
  { path: 'viewAllProjects', loadChildren: './view-all-projects/view-all-projects.module#ViewAllProjectsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewStudent', loadChildren: './create-new-student/create-new-student.module#CreateNewStudentPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllStudents', loadChildren: './view-all-students/view-all-students.module#ViewAllStudentsPageModule', canActivate: [AuthGuard] },
  { path: 'viewStudentDetails', loadChildren: './view-student-details/view-student-details.module#ViewStudentDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewStudentDetails/:userId', loadChildren: './view-student-details/view-student-details.module#ViewStudentDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllCompanies', loadChildren: './view-all-companies/view-all-companies.module#ViewAllCompaniesPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails/:userId', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewReview', loadChildren: './create-new-review/create-new-review.module#CreateNewReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewCompanyReview', loadChildren: './create-new-company-review/create-new-company-review.module#CreateNewCompanyReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewStudentReview', loadChildren: './create-new-student-review/create-new-student-review.module#CreateNewStudentReviewPageModule', canActivate: [AuthGuard] }


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
