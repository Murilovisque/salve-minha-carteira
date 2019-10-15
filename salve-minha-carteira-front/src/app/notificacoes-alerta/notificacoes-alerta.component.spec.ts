import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificacoesAlertaComponent } from './notificacoes-alerta.component';

describe('NotificacoesAlertaComponent', () => {
  let component: NotificacoesAlertaComponent;
  let fixture: ComponentFixture<NotificacoesAlertaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificacoesAlertaComponent ]
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
