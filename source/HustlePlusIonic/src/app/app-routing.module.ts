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
  { path: 'createNewStudent', loadChildren: './create-new-student/create-new-student.module#CreateNewStudentPageModule' },
  { path: 'profile', loadChildren: './profile/profile.module#ProfilePageModule', canActivate: [AuthGuard] },
  { path: 'profile/:userId', loadChildren: './profile/profile.module#ProfilePageModule', canActivate: [AuthGuard] },
  { path: 'viewStudentDetails', loadChildren: './view-student-details/view-student-details.module#ViewStudentDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewStudentDetails/:userId', loadChildren: './view-student-details/view-student-details.module#ViewStudentDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllProjects', loadChildren: './view-all-projects/view-all-projects.module#ViewAllProjectsPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllStudents', loadChildren: './view-all-students/view-all-students.module#ViewAllStudentsPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllCompanies', loadChildren: './view-all-companies/view-all-companies.module#ViewAllCompaniesPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails/:userId', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewMyTeams', loadChildren: './view-my-teams/view-my-teams.module#ViewMyTeamsPageModule', canActivate: [AuthGuard] },
  { path: 'viewTeamDetails', loadChildren: './view-team-details/view-team-details.module#ViewTeamDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewTeamDetails/:teamId', loadChildren: './view-team-details/view-team-details.module#ViewTeamDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewReview', loadChildren: './create-new-review/create-new-review.module#CreateNewReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewCompanyReview', loadChildren: './create-new-company-review/create-new-company-review.module#CreateNewCompanyReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewStudentReview', loadChildren: './create-new-student-review/create-new-student-review.module#CreateNewStudentReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewTeam', loadChildren: './create-new-team/create-new-team.module#CreateNewTeamPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllApplications', loadChildren: './view-all-applications/view-all-applications.module#ViewAllApplicationsPageModule', canActivate: [AuthGuard] },  {
    path: 'create-new-skill',
    loadChildren: () => import('./create-new-skill/create-new-skill.module').then( m => m.CreateNewSkillPageModule)
  },
  {
    path: 'update-profile-modal',
    loadChildren: () => import('./update-profile-modal/update-profile-modal.module').then( m => m.UpdateProfileModalPageModule)
  },

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
