import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateNewSkillPage } from './create-new-skill.page';

const routes: Routes = [
  {
    path: '',
    component: CreateNewSkillPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CreateNewSkillPageRoutingModule {}
