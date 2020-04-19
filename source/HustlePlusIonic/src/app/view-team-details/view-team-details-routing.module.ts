import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewTeamDetailsPage } from './view-team-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewTeamDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewTeamDetailsPageRoutingModule {}
