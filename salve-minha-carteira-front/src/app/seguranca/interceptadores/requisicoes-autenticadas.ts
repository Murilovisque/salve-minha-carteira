import { HttpInterceptor, HttpHandler, HttpEvent, HttpRequest, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, of, throwError} from "rxjs";
import { Recursos } from 'src/app/global/recursos';
import { TokenService } from 'src/app/token/token.service';
import { catchError, switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { UsuarioNaoAutenticadoError } from 'src/app/global/erro.service';

@Injectable()
export class RequisicoesAutenticadas implements HttpInterceptor {

    static readonly RECURSOS_PUBLICOS = `${Recursos.API_USUARIOS}|${Recursos.API_USUARIOS_AUTENTICAR}`

    constructor(private tokenService: TokenService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.match(RequisicoesAutenticadas.RECURSOS_PUBLICOS))
            return next.handle(req);
        
        return this.tokenService.obterTokenAutenticacao().pipe(
            switchMap(token => {                
                let authReq = req.clone({ headers: req.headers.set("Authorization", `Bearer ${token.hash}`) });
                return next.handle(authReq);
            }),
            catchError((err) => {
                if (err instanceof UsuarioNaoAutenticadoError) {
                    this.tokenService.removerTokenAutenticacao().subscribe();
                    return throwError(new HttpErrorResponse({status: 401, statusText: "401"}))
                } else {
                    if (err instanceof HttpErrorResponse && err.status == 401)
                        this.tokenService.removerTokenAutenticacao().subscribe();
                    return throwError(err);
                }
            })
        );
    }
}