export class Notificacao {
    titulo: string
    corpo: string
    tipo: TipoNotificacao

    constructor(ptitulo: string, pcorpo: string, ptipo: TipoNotificacao) {
        this.titulo = ptitulo;
        this.corpo = pcorpo;
        this.tipo = ptipo;
    }
}

export enum TipoNotificacao {
    SUCESSO = 'success',
    ERRO = 'danger',
    INFO = 'info'
}