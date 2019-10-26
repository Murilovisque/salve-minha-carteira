import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, retryWhen, tap } from 'rxjs/operators';

import { Errors } from '../errors';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  static readonly URL_USUARIOS = 'api/usuarios'
  static readonly URL_USUARIOS_AUTENTICAR = UsuarioService.URL_USUARIOS + '/autenticar'

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  cadastrar(nome: string, email: string, senha: string): Observable<any> {    
    let params = new HttpParams().set('nome', nome.trim()).append('email', email).append('senha', senha)
    return this.http.post(UsuarioService.URL_USUARIOS, params).
      pipe(retryWhen(Errors.retentarRequisicaoHTTP), catchError(Errors.mapearErro))
  }

  autenticar(email: string, senha: string) {
    let params = new HttpParams().set('email', email).append('senha', senha);
    return this.http.post<Token>(UsuarioService.URL_USUARIOS_AUTENTICAR, params).
      pipe(tap(token => this.tokenService.configurarTokenAutenticacao(token.hash)), retryWhen(Errors.retentarRequisicaoHTTP), catchError(Errors.mapearErro))
  }
}


class Token {
  hash: string
}