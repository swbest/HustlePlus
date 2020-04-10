import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewAllProjectsPage } from './view-all-projects.page';

const routes: Routes = [
  {
    path: '',
    component: ViewAllProjectsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewAllProjectsPageRoutingModule {}
