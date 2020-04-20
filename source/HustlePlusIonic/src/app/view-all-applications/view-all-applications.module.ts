import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllApplicationsPageRoutingModule } from './view-all-applications-routing.module';

import { ViewAllApplicationsPage } from './view-all-applications.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllApplicationsPageRoutingModule
  ],
  declarations: [ViewAllApplicationsPage]
})
export class ViewAllApplicationsPageModule {}
