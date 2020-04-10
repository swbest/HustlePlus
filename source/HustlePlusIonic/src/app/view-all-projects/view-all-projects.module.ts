import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllProjectsPageRoutingModule } from './view-all-projects-routing.module';

import { ViewAllProjectsPage } from './view-all-projects.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllProjectsPageRoutingModule
  ],
  declarations: [ViewAllProjectsPage]
})
export class ViewAllProjectsPageModule {}
