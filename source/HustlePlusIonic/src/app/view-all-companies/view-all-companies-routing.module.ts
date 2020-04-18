import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllCompaniesPage } from './view-all-companies.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllCompaniesPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllCompaniesPageRoutingModule {}
