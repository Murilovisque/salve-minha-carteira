import { Component, OnInit } from '@angular/core';
import { BoletaService } from 'src/app/boleta/boleta.service';
import { BoletaAgrupadoPeloCodigoNegociacao } from 'src/app/boleta/boleta';
import { SalveMinhaCarteiraError, ErroService } from 'src/app/global/erro.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  boletasAgrupadas: BoletaAgrupadoPeloCodigoNegociacao[]

  constructor(private boletaService: BoletaService, private erroService: ErroService) { }

  ngOnInit() {
    this.boletaService.obterBoletasAgrupadas().subscribe(
      boletas => this.boletasAgrupadas = boletas,
      err => this.erroService.tratar(err as SalveMinhaCarteiraError)
    );
  }

}
