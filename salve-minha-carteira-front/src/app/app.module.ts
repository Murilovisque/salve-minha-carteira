import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { UsuarioModule } from './usuario/usuario.module';
import { NotificacaoModule } from './notificacao/notificacao.module';
import { TokenModule } from './token/token.module';
import { PainelModule } from './painel/painel.module';
import { BoletaModule } from './boleta/boleta.module';
import { SegurancaModule } from './seguranca/seguranca.module';
import { HTTP_INTERCEPTOR_PROVIDER } from './seguranca/interceptadores/config';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    NgbModule,
    UsuarioModule,
    NotificacaoModule,
    AppRoutingModule,
    TokenModule,
    PainelModule,
    BoletaModule,
    SegurancaModule,
  ],
  providers: [
    HTTP_INTERCEPTOR_PROVIDER
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
