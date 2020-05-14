export class Recursos {
    static readonly PAGINA_CADASTRO_USUARIO = "cadastro";
    static readonly PAGINA_LOGIN = "login";
    static readonly PAGINA_PAINEL_HOME = "painel";
    static readonly PAGINA_PAINEL_CADASTRO_BOLETA = "painel/cadastro-boleta";

    static readonly API_USUARIOS = 'api/usuarios';
    static readonly API_USUARIOS_AUTENTICAR = Recursos.API_USUARIOS + '/autenticar';
    static readonly API_BOLETAS = 'api/boletas';
    static readonly API_ACOES = 'api/acoes';
    static readonly API_ACOES_PROCURAR = Recursos.API_ACOES + '/procurar';
}