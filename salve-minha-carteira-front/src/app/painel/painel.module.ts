import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PainelRoutingModule } from './painel-routing.module';
import { HomeComponent } from './home/home.component';

import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    PainelRoutingModule,
    NgbAlertModule
  ]
})
export class PainelModule { }
