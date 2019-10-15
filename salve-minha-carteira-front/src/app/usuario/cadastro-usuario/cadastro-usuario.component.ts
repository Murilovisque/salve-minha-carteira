import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors, ValidatorFn } from '@angular/forms';
import { UsuarioService } from '../usuario.service';
import { JaExisteError, SalveMinhaCarteiraError } from 'src/app/errors';
import { NotificadorService } from 'src/app/notificacao/notificador.service';

@Component({
  selector: 'app-cadastro-usuario',
  templateUrl: './cadastro-usuario.component.html',
  styleUrls: ['./cadastro-usuario.component.css']
})
export class CadastroUsuarioComponent implements OnInit {

  cadastroRealizado = false;
  usuario = new Usuario();

  usuarioForm = this.fb.group({
    nome: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.email]],
    senha: ['', [Validators.required, Validators.required, Validators.minLength(4), Validators.maxLength(30)]],
    confirmaSenha: ['', [Validators.required, Validators.required, Validators.minLength(4), Validators.maxLength(30)]]
  }, {validators: validaMesmaSenha()})

  constructor(private fb: FormBuilder, private usuarioService: UsuarioService, private notificadorService: NotificadorService) { }

  ngOnInit() {
  }

  cadastrar() {
    this.usuarioService.cadastrar(this.obterValorForm('nome'), this.obterValorForm('email'), this.obterValorForm('senha')).subscribe(
      () => {
        this.notificadorService.adicionarAlertaSucesso("Cadastro realizado!");
        this.cadastroRealizado = true;
      },
      (err) => {
        let msg = err instanceof JaExisteError ? "Usuario com email já cadastrado" : (err as SalveMinhaCarteiraError).message;
        this.notificadorService.adicionarAlertaErro(msg);
      })
  }

  private obterValorForm(campo: string): string {
    return this.usuarioForm.get(campo).value as string
  }
}

function validaMesmaSenha(): ValidatorFn {
  return (control: FormGroup): ValidationErrors | null => {
    return control.get('senha') && control.get('confirmaSenha')
      && control.get('confirmaSenha').value !== control.get('senha').value
      ? { 'senhaNaoConfirmada': true } : null
  }
}

class Usuario {
  nome: string
  email: string
  senha: string
  confirmaSenha: string
}
