import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';

import { NotificacoesAlertaComponent } from './alerta/notificacoes-alerta.component';

@NgModule({
  declarations: [
    NotificacoesAlertaComponent,
  ],
  imports: [
    CommonModule,
    NgbAlertModule
  ],
  exports: [
    NotificacoesAlertaComponent
  ]
})
export class NotificacaoModule { }
