import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllStudentsPage } from './view-all-students.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllStudentsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllStudentsPageRoutingModule {}
