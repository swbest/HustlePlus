import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewCompanyDetailsPage } from './view-company-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewCompanyDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewCompanyDetailsPageRoutingModule {}
