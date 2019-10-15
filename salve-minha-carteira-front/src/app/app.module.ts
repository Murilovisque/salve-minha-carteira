import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { UsuarioModule } from './usuario/usuario.module';
import { NotificacoesAlertaComponent } from './notificacoes-alerta/notificacoes-alerta.component';
import { NotificacaoModule } from './notificacao/notificacao.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NotificacoesAlertaComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    NgbModule,
    UsuarioModule,
    AppRoutingModule,
    NotificacaoModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
