import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../../usuario/usuario.service';
import { UsuarioNaoAutenticadoError, SalveMinhaCarteiraError } from 'src/app/global/erro.service';
import { NotificadorService } from 'src/app/notificacao/notificador.service';
import { Router } from '@angular/router';
import { Recursos } from 'src/app/global/recursos';

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

	constructor(private usuarioService: UsuarioService, public notificadorService: NotificadorService, private router: Router) { }

	ngOnInit() {
	}

	autenticar() {
		this.usuarioService.autenticar(this.obterValorForm('email'), this.obterValorForm('senha')).subscribe(
		() => this.router.navigate([Recursos.PAGINA_PAINEL_HOME]),
		(err) => {
			let msg = err instanceof UsuarioNaoAutenticadoError ? "Email ou senha inválidos" : (err as SalveMinhaCarteiraError).message;
			this.loginForm.get('senha').setValue('');
			this.notificadorService.adicionarAlertaErro(msg);
		});
	}

	private obterValorForm(campo: string): string {
		return this.loginForm.get(campo).value as string
	}
}
