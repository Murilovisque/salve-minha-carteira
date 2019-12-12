import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { UsuarioService } from '../usuario.service';
import { NotificadorService } from 'src/app/notificacao/notificador.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let usuarioService: UsuarioService;
  let notificadorService: NotificadorService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [
        CommonModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [
        { provide: UsuarioService },
        { provide: NotificadorService, useClass: NotificadorServiceMock }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    usuarioService = TestBed.get(UsuarioService);
    notificadorService = TestBed.get(NotificadorService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

class UsuarioServiceMock {
    
}

class NotificadorServiceMock {
  removerAlerta() {

  } 
}