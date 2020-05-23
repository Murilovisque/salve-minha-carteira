import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivateChild, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { TokenService } from '../../token/token.service';
import { Recursos } from 'src/app/global/recursos';

@Injectable({
  providedIn: 'root'
})
export class RecursosGuard implements CanActivate, CanActivateChild {

  constructor(private tokenService: TokenService, private router: Router) {}
  
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.tokenService.obterTokenAutenticacao().pipe(
      switchMap(_ => of(true)),
      catchError(_ => {
        this.router.navigate([Recursos.PAGINA_LOGIN]);
        return of(false);
      })
    );
  }
  
  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.canActivate(childRoute, state);
  }

  
}
