import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewMyTeamsPage } from './view-my-teams.page';

const routes: Routes = [
  {
    path: '',
    component: ViewMyTeamsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewMyTeamsPageRoutingModule {}
