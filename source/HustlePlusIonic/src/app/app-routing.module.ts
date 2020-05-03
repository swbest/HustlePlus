import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './guards/auth.guard';


const routes: Routes = [
  {
    path: '',
    redirectTo: 'slides',
    pathMatch: 'full'
  },

  {
    path: 'slides',
    loadChildren: () => import('./slides/slides.module').then( m => m.SlidesPageModule)
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
  { path: 'viewAllCompanies', loadChildren: './view-all-companies/view-all-companies.module#ViewAllCompaniesPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewCompanyDetails/:userId', loadChildren: './view-company-details/view-company-details.module#ViewCompanyDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewTeamDetails', loadChildren: './view-team-details/view-team-details.module#ViewTeamDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewTeamDetails/:teamId', loadChildren: './view-team-details/view-team-details.module#ViewTeamDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewCompanyReview', loadChildren: './create-new-company-review/create-new-company-review.module#CreateNewCompanyReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewStudentReview', loadChildren: './create-new-student-review/create-new-student-review.module#CreateNewStudentReviewPageModule', canActivate: [AuthGuard] },
  { path: 'createNewTeam', loadChildren: './create-new-team/create-new-team.module#CreateNewTeamPageModule', canActivate: [AuthGuard] },
  { path: 'createNewSkill', loadChildren: './create-new-skill/create-new-skill.module#CreateNewSkillPageModule', canActivate: [AuthGuard] },
  { path: 'updateProfileModal', loadChildren: './update-profile-modal/update-profile-modal.module#UpdateProfileModalPageModule', canActivate: [AuthGuard] },
  { path: 'viewProjectDetails', loadChildren: './view-project-details/view-project-details.module#ViewProjectDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewProjectDetails/:projectId', loadChildren: './view-project-details/view-project-details.module#ViewProjectDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'reviews', loadChildren: './reviews/reviews.module#ReviewsPageModule', canActivate: [AuthGuard] },
  { path: 'teams', loadChildren: './teams/teams.module#TeamsPageModule', canActivate: [AuthGuard] },
  { path: 'projects', loadChildren: './projects/projects.module#ProjectsPageModule', canActivate: [AuthGuard] },
  { path: 'applications', loadChildren: './applications/applications.module#ApplicationsPageModule', canActivate: [AuthGuard] },
  { path: 'viewMyProjectDetails', loadChildren: './view-my-project-details/view-my-project-details.module#ViewMyProjectDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'viewMyProjectDetails/:projectId', loadChildren: './view-my-project-details/view-my-project-details.module#ViewMyProjectDetailsPageModule', canActivate: [AuthGuard] },
  { path: 'resume', loadChildren: './resume/resume.module#ResumePageModule', canActivate: [AuthGuard] },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
