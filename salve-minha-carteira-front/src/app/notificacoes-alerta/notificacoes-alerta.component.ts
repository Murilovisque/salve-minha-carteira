import { Component, OnInit } from '@angular/core';
import { NotificadorService } from '../services/notificador.service';
import { Notificacao } from '../entidades/notificacao';

@Component({
  selector: 'app-notificacoes-alerta',
  templateUrl: './notificacoes-alerta.component.html',
  styleUrls: ['./notificacoes-alerta.component.css']
})
export class NotificacoesAlertaComponent implements OnInit {

  notif: Notificacao

  constructor(public notificadorService: NotificadorService) { }

  ngOnInit() {
  }

  ngDoCheck() {
    if (this.notificadorService.temAlerta())
      this.notif = this.notificadorService.obterAlerta();
  }

}
