import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BoletaAgrupadoPeloCodigoNegociacao } from './boleta';
import { Recursos } from '../global/recursos';
import { retryWhen, catchError, tap, map } from 'rxjs/operators';
import { ErroService } from '../global/erro.service';
import { plainToClass } from 'class-transformer'

@Injectable({
  providedIn: 'root'
})
export class BoletaService {

	constructor(private http: HttpClient, private errosService: ErroService) { }

	obterBoletasAgrupadas(): Observable<BoletaAgrupadoPeloCodigoNegociacao[]> {
		return this.http.get<BoletaAgrupadoPeloCodigoNegociacao[]>(Recursos.API_BOLETAS).pipe(
			map(boletas => plainToClass(BoletaAgrupadoPeloCodigoNegociacao, boletas)),
			retryWhen(this.errosService.retentarRequisicaoHTTP), catchError(this.errosService.mapearErro)
		)
	}
}
