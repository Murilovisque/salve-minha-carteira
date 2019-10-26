import { Injectable } from '@angular/core';
import { Token } from './token';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private tokenAutenticacao: Token 

  constructor() { }

  configurarTokenAutenticacao(hash: string) {
    this.tokenAutenticacao = new Token(hash);
  }
}
