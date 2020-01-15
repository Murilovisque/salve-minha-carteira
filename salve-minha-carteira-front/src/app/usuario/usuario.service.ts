import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, retryWhen, tap } from 'rxjs/operators';

import { TokenService } from '../token/token.service';
import { Recursos } from '../global/recursos';
import { ErroService } from '../global/erro.service';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

	constructor(private http: HttpClient, private tokenService: TokenService, private erroService: ErroService) { }

	cadastrar(nome: string, email: string, senha: string): Observable<any> {    
		let params = new HttpParams().set('nome', nome.trim()).append('email', email).append('senha', senha)
		return this.http.post(Recursos.API_USUARIOS, params).
		pipe(retryWhen(this.erroService.retentarRequisicaoHTTP), catchError(this.erroService.mapearErro))
	}

	autenticar(email: string, senha: string) {
		let params = new HttpParams().set('email', email).append('senha', senha);
		return this.http.post<Token>(Recursos.API_USUARIOS_AUTENTICAR, params).
			pipe(
				tap(token => this.tokenService.configurarTokenAutenticacao(token.hash).subscribe()),
				retryWhen(this.erroService.retentarRequisicaoHTTP),
				catchError(this.erroService.mapearErro))
	}
}

class Token {
  hash: string
}