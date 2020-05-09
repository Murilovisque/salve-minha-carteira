import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BoletaRoutingModule } from './boleta-routing.module';
import { CadastroBoletaComponent } from './cadastro-boleta/cadastro-boleta.component';

import { NgbButtonsModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [CadastroBoletaComponent],
  imports: [
    CommonModule,
    BoletaRoutingModule,
    FormsModule,
    NgbButtonsModule,
    NgbDropdownModule
  ]
})
export class BoletaModule { }
