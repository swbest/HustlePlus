import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllApplicationsPage } from './view-all-applications.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllApplicationsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllApplicationsPageRoutingModule {}
