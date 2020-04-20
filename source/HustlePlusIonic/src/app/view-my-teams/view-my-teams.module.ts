import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewMyTeamsPageRoutingModule } from './view-my-teams-routing.module';

import { ViewMyTeamsPage } from './view-my-teams.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewMyTeamsPageRoutingModule
  ],
  declarations: [ViewMyTeamsPage]
})
export class ViewMyTeamsPageModule {}
