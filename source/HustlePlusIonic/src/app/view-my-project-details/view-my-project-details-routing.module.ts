import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewMyProjectDetailsPage } from './view-my-project-details.page';

const routes: Routes = [
  {
    path: '',
    component: ViewMyProjectDetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewMyProjectDetailsPageRoutingModule {}
