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
  { path: 'viewAllProjects', loadChildren: './view-all-projects/view-all-projects.module#ViewAllProjectsPageModule', canActivate: [AuthGuard] },
  { path: 'createNewStudent', loadChildren: './create-new-student/create-new-student.module#CreateNewStudentPageModule', canActivate: [AuthGuard] },
  { path: 'viewAllStudents', loadChildren: './view-all-students/view-all-students.module#ViewAllStudentsPageModule', canActivate: [AuthGuard] },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})

export class AppRoutingModule { }
