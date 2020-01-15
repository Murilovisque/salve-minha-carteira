import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Recursos } from '../global/recursos';
import { HomeComponent } from './home/home.component';


const routes: Routes = [
  {path: Recursos.PAGINA_PAINEL_HOME, component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PainelRoutingModule { }
