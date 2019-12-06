import { Injectable } from '@angular/core';
import { Token } from './token';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  static readonly LOCAL_STORAGE_TOKEN_KEY = 'TokenService-token'
  private tokenAutenticacao: Token 

  constructor() { }

  configurarTokenAutenticacao(hash: string) {
    this.tokenAutenticacao = new Token(hash);    
    localStorage.setItem(TokenService.LOCAL_STORAGE_TOKEN_KEY, JSON.stringify(this.tokenAutenticacao));
  }
}
