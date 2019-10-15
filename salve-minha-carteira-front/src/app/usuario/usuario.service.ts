import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Errors } from '../errors';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  URL_USUARIOS = 'api/usuarios'

  constructor(private http: HttpClient) { }

  cadastrar(nome: string, email: string, senha: string): Observable<any> {    
    return this.http.post(this.URL_USUARIOS, new HttpParams().set('nome', nome).append('email', email).append('senha', senha)).
      pipe(
        retry(1),
        catchError((err) => Errors.mapearErro(err))
      )
  }
}
