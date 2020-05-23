import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Recursos } from '../global/recursos';
import { HomeComponent } from './home/home.component';
import { RecursosGuard } from '../seguranca/interceptadores/recursos.guard';
import { CadastroBoletaComponent } from '../boleta/cadastro-boleta/cadastro-boleta.component';


const routes: Routes = [
  {
    path: Recursos.PAGINA_PAINEL_HOME,    
    canActivate: [RecursosGuard],
    children: [
      {
        path: '',
        canActivateChild: [RecursosGuard],
        children: [
          { path: '',  component: HomeComponent },
          { path: Recursos.PAGINA_CADASTRO_BOLETA, component: CadastroBoletaComponent }
        ]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PainelRoutingModule { }
