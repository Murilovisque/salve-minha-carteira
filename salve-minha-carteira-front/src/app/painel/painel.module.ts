import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PainelRoutingModule } from './painel-routing.module';
import { HomeComponent } from './home/home.component';

import { BoletaModule } from '../boleta/boleta.module';


@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    PainelRoutingModule,
    BoletaModule
  ]
})
export class PainelModule { }
