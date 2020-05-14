import { Component, OnInit } from '@angular/core';
import { Acao } from 'src/app/acao/acao';
import { AcaoService } from 'src/app/acao/acao.service';

@Component({
  selector: 'app-cadastro-boleta',
  templateUrl: './cadastro-boleta.component.html',
  styleUrls: ['./cadastro-boleta.component.css']
})
export class CadastroBoletaComponent implements OnInit {

  tipo :string
  termo :string
  acoesEncontradas: Acao[]

  constructor(private acaoService: AcaoService) { }

  ngOnInit() {
    
  }

}
