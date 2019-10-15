import { Injectable } from '@angular/core';
import { Notificacao, TipoNotificacao } from '../entidades/notificacao';

@Injectable({
  providedIn: 'root'
})
export class NotificadorService {

  private alerta: Notificacao

  constructor() { }

  public temAlerta(): boolean {
    return this.alerta != null;
  }

  public obterAlerta(): Notificacao {
    return this.alerta;
  }

  public removerAlerta() {
      this.alerta = null;
  }

  public adicionarAlertaSucesso(corpo: string) {
    this.alerta = new Notificacao("Sucesso", corpo, TipoNotificacao.SUCESSO);
  }

  public adicionarAlertaErro(corpo: string) {
    this.alerta = new Notificacao("Erro", corpo, TipoNotificacao.ERRO);
  }
}
