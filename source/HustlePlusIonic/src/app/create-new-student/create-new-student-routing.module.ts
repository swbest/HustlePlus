import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewStudentPage } from './create-new-student.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewStudentPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewStudentPageRoutingModule {}
