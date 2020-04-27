import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewProjectDetailsPageRoutingModule } from './view-project-details-routing.module';

import { ViewProjectDetailsPage } from './view-project-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewProjectDetailsPageRoutingModule
  ],
  declarations: [ViewProjectDetailsPage]
})
export class ViewProjectDetailsPageModule {}
