import { Component, OnInit, DoCheck } from '@angular/core';
import { Notificacao } from 'src/app/entidades/notificacao';
import { NotificadorService } from '../notificador.service';

@Component({
  selector: 'app-notificacoes-alerta',
  templateUrl: './notificacoes-alerta.component.html',
  styleUrls: ['./notificacoes-alerta.component.css']
})
export class NotificacoesAlertaComponent implements OnInit, DoCheck {

  notif: Notificacao

  constructor(public notificadorService: NotificadorService) { }

  ngOnInit() {
  }

  ngDoCheck() {
    if (this.notificadorService.temAlerta())
      this.notif = this.notificadorService.obterAlerta();
  }

}
