import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CadastroBoletaComponent } from './cadastro-boleta/cadastro-boleta.component';

import { NgbButtonsModule, NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [CadastroBoletaComponent],
  imports: [
    CommonModule,
    FormsModule,
    NgbButtonsModule,
    NgbDropdownModule
  ],
  exports: [
    CadastroBoletaComponent
  ]
})
export class BoletaModule { }
