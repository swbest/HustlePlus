import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UpdateProfileModalPageRoutingModule } from './update-profile-modal-routing.module';

import { UpdateProfileModalPage } from './update-profile-modal.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UpdateProfileModalPageRoutingModule
  ],
  declarations: [UpdateProfileModalPage]
})
export class UpdateProfileModalPageModule {}
