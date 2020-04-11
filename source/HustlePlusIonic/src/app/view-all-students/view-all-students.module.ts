import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewAllStudentsPageRoutingModule } from './view-all-students-routing.module';

import { ViewAllStudentsPage } from './view-all-students.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewAllStudentsPageRoutingModule
  ],
  declarations: [ViewAllStudentsPage]
})
export class ViewAllStudentsPageModule {}
