import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewStudentPageRoutingModule } from './create-new-student-routing.module';

import { CreateNewStudentPage } from './create-new-student.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewStudentPageRoutingModule
  ],
  declarations: [CreateNewStudentPage]
})
export class CreateNewStudentPageModule {}
