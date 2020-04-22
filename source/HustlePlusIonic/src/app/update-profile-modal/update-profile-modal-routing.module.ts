import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UpdateProfileModalPage } from './update-profile-modal.page';

const routes: Routes = [
  {
    path: '',
    component: UpdateProfileModalPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UpdateProfileModalPageRoutingModule {}
