import { Component, OnInit, DoCheck } from '@angular/core';
import { Notificacao } from 'src/app/global/notificacao';
import { NotificadorService } from '../notificador.service';

@Component({
  selector: 'app-notificacoes-alerta',
  templateUrl: './notificacoes-alerta.component.html',
  styleUrls: ['./notificacoes-alerta.component.css']
})
export class NotificacoesAlertaComponent {

  constructor(public notificadorService: NotificadorService) { }

}
