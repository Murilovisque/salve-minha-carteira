import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CadastroBoletaComponent } from './cadastro-boleta/cadastro-boleta.component';
import { Recursos } from '../global/recursos';

const routes: Routes = [
  {path: Recursos.PAGINA_PAINEL_CADASTRO_BOLETA, component: CadastroBoletaComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BoletaRoutingModule { }
