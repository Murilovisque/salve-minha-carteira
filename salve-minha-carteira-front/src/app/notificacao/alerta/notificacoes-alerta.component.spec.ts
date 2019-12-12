import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificacoesAlertaComponent } from './notificacoes-alerta.component';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { NotificadorService } from '../notificador.service';

describe('NotificacoesAlertaComponent', () => {
  let component: NotificacoesAlertaComponent;
  let fixture: ComponentFixture<NotificacoesAlertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificacoesAlertaComponent ],
      imports: [
        CommonModule,
        NgbAlertModule
      ],
      providers: [
        { provide: NotificadorService, useClass: NotificadorServiceMock }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificacoesAlertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

class NotificadorServiceMock {
  temAlerta() {
  } 
}