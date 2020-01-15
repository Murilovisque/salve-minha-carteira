import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Recursos } from './global/recursos';


const routes: Routes = [
  {path: "", redirectTo: Recursos.PAGINA_LOGIN, pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
