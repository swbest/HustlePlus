import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewCompanyDetailsPageRoutingModule } from './view-company-details-routing.module';

import { ViewCompanyDetailsPage } from './view-company-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewCompanyDetailsPageRoutingModule
  ],
  declarations: [ViewCompanyDetailsPage]
})
export class ViewCompanyDetailsPageModule {}
