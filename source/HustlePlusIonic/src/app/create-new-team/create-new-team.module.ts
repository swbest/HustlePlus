import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewTeamPageRoutingModule } from './create-new-team-routing.module';

import { CreateNewTeamPage } from './create-new-team.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewTeamPageRoutingModule
  ],
  declarations: [CreateNewTeamPage]
})
export class CreateNewTeamPageModule {}
