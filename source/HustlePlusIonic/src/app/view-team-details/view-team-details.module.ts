import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewTeamDetailsPageRoutingModule } from './view-team-details-routing.module';

import { ViewTeamDetailsPage } from './view-team-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewTeamDetailsPageRoutingModule
  ],
  declarations: [ViewTeamDetailsPage]
})
export class ViewTeamDetailsPageModule {}
