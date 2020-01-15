import { TestBed } from '@angular/core/testing';

import { BoletaService } from './boleta.service';

describe('BoletaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BoletaService = TestBed.get(BoletaService);
    expect(service).toBeTruthy();
  });
});
