import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ErroService } from '../global/erro.service';
import { Observable } from 'rxjs';
import { Recursos } from '../global/recursos';
import { Acao } from './acao';
import { retryWhen, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AcaoService {

  constructor(private http: HttpClient, private errosService: ErroService) { }

  procurarAcoes(termo: string): Observable<Acao[]> {
    return this.http.get<Acao[]>(Recursos.API_ACOES_PROCURAR + `?argumento-pesquisa=${termo}`)
      .pipe(
        retryWhen(this.errosService.retentarRequisicaoHTTP),
				catchError(this.errosService.mapearErro)
      );
  }
}
