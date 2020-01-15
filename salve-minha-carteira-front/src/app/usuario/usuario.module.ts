import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { CadastroUsuarioComponent } from './cadastro-usuario/cadastro-usuario.component';
import { ReactiveFormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'

@NgModule({
  declarations: [
    CadastroUsuarioComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    UsuarioRoutingModule
  ]
})
export class UsuarioModule { }
