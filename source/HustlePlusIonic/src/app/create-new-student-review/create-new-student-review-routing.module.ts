import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewStudentReviewPage } from './create-new-student-review.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewStudentReviewPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewStudentReviewPageRoutingModule {}
