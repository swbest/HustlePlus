import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewStudentDetailsPage } from './view-student-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewStudentDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewStudentDetailsPageRoutingModule {}
