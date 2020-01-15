import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, timer } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NotificadorService } from '../notificacao/notificador.service';
import { Recursos } from './recursos';

@Injectable({
  providedIn: 'root'
})
export class ErroService {

	constructor(private router: Router, private notificaoService: NotificadorService) { }

	mapearErro(err :any) :Observable<never> {
		if (err instanceof HttpErrorResponse) {
			switch(err.status) {
				case 400:
					return throwError(new ArgumentosInvalidosError(err.error.message));
				case 409:
					return throwError(new JaExisteError(err.error));
				case 401:
					return throwError(new UsuarioNaoAutenticadoError());                
			}
		}
		return throwError(new SalveMinhaCarteiraError())
	}

	retentarRequisicaoHTTP(tentativas: Observable<any>, quantidadeRetentativas: number = 2, intervaloEntreRetentativas: number = 500): Observable<any> {
		return tentativas.pipe(mergeMap((error, i) => {
			if (error instanceof HttpErrorResponse && (error.status == 0 || error.status >= 500) && i + 1 < quantidadeRetentativas)
				return timer(intervaloEntreRetentativas);
			return throwError(error);          
		}))
	}

	tratarRecursosAutenticados(ex: SalveMinhaCarteiraError) {
		this.notificaoService.adicionarAlertaErro(ex.message);
		if (ex instanceof UsuarioNaoAutenticadoError)
			this.router.navigate([Recursos.PAGINA_LOGIN]);	  	
	}
}

export class SalveMinhaCarteiraError implements Error {
	name: string;
	message: string;
	stack?: string;

	constructor(message? :string) {
		this.message = message == null ? "Não foi possível processar sua requisição, tente novamente mais tarde" : message;
		this.name = this.getName();
	}

	getName() :string {
		return "SalveMinhaCarteiraError";
	}
}

export class JaExisteError extends SalveMinhaCarteiraError {
	constructor(message :string) {
		super(message == null ? "Já cadastrado" : message);
	}

	getName() :string {
		return "BadRequestError";
	}
}

export class ArgumentosInvalidosError extends SalveMinhaCarteiraError {
	constructor(message :string) {
		super(message == null ? "Já cadastrado" : message);
	}

	getName() :string {
		return "ArgumentosInvalidosError";
	}
}

export class UsuarioNaoAutenticadoError extends SalveMinhaCarteiraError {
	constructor() {
		super("Usuário não autenticado");
	}

	getName() :string {
		return "UsuarioNaoAutenticadoError";
	}
}