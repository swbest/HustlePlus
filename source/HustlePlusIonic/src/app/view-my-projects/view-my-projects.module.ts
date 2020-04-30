import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewMyProjectsPageRoutingModule } from './view-my-projects-routing.module';

import { ViewMyProjectsPage } from './view-my-projects.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewMyProjectsPageRoutingModule
  ],
  declarations: [ViewMyProjectsPage]
})
export class ViewMyProjectsPageModule {}
