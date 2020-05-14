import { Injectable } from '@angular/core';
import { Notificacao, TipoNotificacao } from '../global/notificacao';

@Injectable({
  providedIn: 'root'
})
export class NotificadorService {

  private alertas: Notificacao[] = []

  constructor() { }

  public obterAlertas(): Notificacao[] {
    return this.alertas;
  }

  public removerAlerta(notif: Notificacao = null) {
      if (notif == null) {
        if (this.alertas.length > 0) {
          this.alertas.shift()
        }
      } else {
        let index = this.alertas.indexOf(notif)
        if (index > -1) {
          this.alertas.splice(index, 1)
        }
      }
  }

  public adicionarAlertaSucesso(corpo: string) {
    this.alertas.push(new Notificacao("Sucesso", corpo, TipoNotificacao.SUCESSO));
  }

  public adicionarAlertaErro(corpo: string) {
    this.alertas.push(new Notificacao("Erro", corpo, TipoNotificacao.ERRO));
  }

  public adicionarAlertaInformativo(corpo: string) {
    this.alertas.push(new Notificacao("Informativo", corpo, TipoNotificacao.INFO));
  }
}
