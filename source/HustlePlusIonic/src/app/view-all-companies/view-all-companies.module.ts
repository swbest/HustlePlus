import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllCompaniesPageRoutingModule } from './view-all-companies-routing.module';

import { ViewAllCompaniesPage } from './view-all-companies.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllCompaniesPageRoutingModule
  ],
  declarations: [ViewAllCompaniesPage]
})
export class ViewAllCompaniesPageModule {}
