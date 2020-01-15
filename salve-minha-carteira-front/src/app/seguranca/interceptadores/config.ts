import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequisicoesAutenticadas } from './requisicoes-autenticadas';

export const HTTP_INTERCEPTOR_PROVIDER = [
    { provide: HTTP_INTERCEPTORS, useClass: RequisicoesAutenticadas, multi: true }
  ]