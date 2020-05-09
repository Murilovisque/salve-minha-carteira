import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastroBoletaComponent } from './cadastro-boleta.component';

describe('CadastroBoletaComponent', () => {
  let component: CadastroBoletaComponent;
  let fixture: ComponentFixture<CadastroBoletaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastroBoletaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastroBoletaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
