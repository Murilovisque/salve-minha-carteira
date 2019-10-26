import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../usuario.service';
import { UsuarioNaoAutenticadoError, SalveMinhaCarteiraError } from 'src/app/errors';
import { NotificadorService } from 'src/app/notificacao/notificador.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup ({
    email: new FormControl('', [Validators.required, Validators.email]),
    senha: new FormControl('', [Validators.required, Validators.minLength(4), Validators.maxLength(30)])
  }) 

  constructor(private usuarioService: UsuarioService, public notificadorService: NotificadorService) { }

  ngOnInit() {
    this.notificadorService.removerAlerta();
  }

  autenticar() {
    this.usuarioService.autenticar(this.obterValorForm('email'), this.obterValorForm('senha')).subscribe(
      () => {        
      },
      (err) => {
        let msg = err instanceof UsuarioNaoAutenticadoError ? "Email ou senha inválidos" : (err as SalveMinhaCarteiraError).message;
        this.loginForm.get('senha').setValue('');
        this.notificadorService.adicionarAlertaErro(msg);
      }
    );
  }

  private obterValorForm(campo: string): string {
    return this.loginForm.get(campo).value as string
  }
}
