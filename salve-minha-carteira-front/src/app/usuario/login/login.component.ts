import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../usuario.service';
import { UsuarioNaoAutenticadoError, SalveMinhaCarteiraError } from 'src/app/errors';

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

  constructor(private usuarioService: UsuarioService, private notificadorService: NotificadorService) { }

  ngOnInit() {
  }

  autenticar() {
    this.usuarioService.autenticar(this.obterValorForm('email'), this.obterValorForm('senha')).subscribe(
      (res) => {
        console.log(console.log(res.body))
      },
      (err) => {
        let msg = err instanceof UsuarioNaoAutenticadoError ? "Email ou senha inválidos" : (err as SalveMinhaCarteiraError).message;
        this.notificadorService.adicionarAlertaErro(msg);
      }
    );
  }

  private obterValorForm(campo: string): string {
    return this.loginForm.get(campo).value as string
  }
}
