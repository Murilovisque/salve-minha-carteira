import { Component, OnInit } from '@angular/core';
import { BoletaService } from 'src/app/boleta/boleta.service';
import { BoletaAgrupadoPeloCodigoNegociacao } from 'src/app/boleta/boleta';
import { SalveMinhaCarteiraError, ErroService } from 'src/app/global/erro.service';
import { NotificadorService } from 'src/app/notificacao/notificador.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  boletasAgrupadas: BoletaAgrupadoPeloCodigoNegociacao[]

  constructor(private boletaService: BoletaService, private erroService: ErroService, private notificadorService: NotificadorService) { }

  ngOnInit() {
    this.boletaService.obterBoletasAgrupadas().subscribe(
      boletas => {
        this.boletasAgrupadas = boletas == null ? [] : boletas
        if (this.boletasAgrupadas.length == 0) {
          this.notificadorService.adicionarAlertaInformativo("Nenhuma ação cadastrada até o momento. Cadastre aqui");
        }
      },
      err => this.erroService.tratarRecursosAutenticados(err as SalveMinhaCarteiraError)
    );
  }

}
