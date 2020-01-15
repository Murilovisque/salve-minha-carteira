import { Injectable } from '@angular/core';
import { Token } from './token';
import { Observable, of, throwError } from 'rxjs';
import { UsuarioNaoAutenticadoError } from '../global/erro.service';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

	static readonly LOCAL_STORAGE_TOKEN_KEY = 'TokenService-token'
	private tokenAutenticacao: Token 

	constructor() { }

	configurarTokenAutenticacao(hash: string): Observable<any> {		
		return new Observable<void>((observer) => {
			this.tokenAutenticacao = new Token(hash);
			localStorage.setItem(TokenService.LOCAL_STORAGE_TOKEN_KEY, JSON.stringify(this.tokenAutenticacao));
			observer.complete();
			return {unsubscribe() {}}
		});
	}
  
	obterTokenAutenticacao(): Observable<Token> {
		if (this.tokenAutenticacao != null)
			return of(this.tokenAutenticacao);
		
		return new Observable<Token>((observer) => {
			this.tokenAutenticacao = JSON.parse(localStorage.getItem(TokenService.LOCAL_STORAGE_TOKEN_KEY));
			if (this.tokenAutenticacao == null) {
				observer.error(new UsuarioNaoAutenticadoError());
			} else {
				observer.next(this.tokenAutenticacao);
				observer.complete();
			}	
			return {unsubscribe() {}};
		});    
	}

	removerTokenAutenticacao(): Observable<any> {
		return new Observable<void>((observer) => {
			localStorage.removeItem(TokenService.LOCAL_STORAGE_TOKEN_KEY);
			observer.complete();
			return {unsubscribe() {}}
		});
	}
}
