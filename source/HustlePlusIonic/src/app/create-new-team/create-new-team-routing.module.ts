import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewTeamPage } from './create-new-team.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewTeamPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewTeamPageRoutingModule {}
