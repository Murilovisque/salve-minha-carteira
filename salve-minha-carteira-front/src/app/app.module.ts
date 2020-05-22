import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms'


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { UsuarioModule } from './usuario/usuario.module';
import { NotificacaoModule } from './notificacao/notificacao.module';
import { TokenModule } from './token/token.module';
import { PainelModule } from './painel/painel.module';
import { SegurancaModule } from './seguranca/seguranca.module';
import { HTTP_INTERCEPTOR_PROVIDER } from './seguranca/interceptadores/config';

import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt, "pt-BR");


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    UsuarioModule,
    NotificacaoModule,
    AppRoutingModule,
    TokenModule,
    PainelModule,
    SegurancaModule,
  ],
  providers: [
    HTTP_INTERCEPTOR_PROVIDER,
    {provide: LOCALE_ID, useValue: 'pt-BR' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

