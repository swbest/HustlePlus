import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewStudentDetailsPageRoutingModule } from './view-student-details-routing.module';

import { ViewStudentDetailsPage } from './view-student-details.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewStudentDetailsPageRoutingModule
  ],
  declarations: [ViewStudentDetailsPage]
})
export class ViewStudentDetailsPageModule {}
