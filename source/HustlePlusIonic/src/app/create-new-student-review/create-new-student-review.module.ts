import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewStudentReviewPageRoutingModule } from './create-new-student-review-routing.module';

import { CreateNewStudentReviewPage } from './create-new-student-review.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewStudentReviewPageRoutingModule
  ],
  declarations: [CreateNewStudentReviewPage]
})
export class CreateNewStudentReviewPageModule {}
