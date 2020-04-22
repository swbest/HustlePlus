import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CreateNewSkillPageRoutingModule } from './create-new-skill-routing.module';

import { CreateNewSkillPage } from './create-new-skill.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CreateNewSkillPageRoutingModule
  ],
  declarations: [CreateNewSkillPage]
})
export class CreateNewSkillPageModule {}
