import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewCompanyReviewPage } from './create-new-company-review.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewCompanyReviewPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewCompanyReviewPageRoutingModule {}
