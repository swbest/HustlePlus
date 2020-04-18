import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewCompanyReviewPageRoutingModule } from './create-new-company-review-routing.module';

import { CreateNewCompanyReviewPage } from './create-new-company-review.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewCompanyReviewPageRoutingModule
  ],
  declarations: [CreateNewCompanyReviewPage]
})
export class CreateNewCompanyReviewPageModule {}
