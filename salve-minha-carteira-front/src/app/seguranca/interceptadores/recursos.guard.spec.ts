import { TestBed } from '@angular/core/testing';

import { RecursosGuard } from './recursos.guard';

describe('RecursosGuard', () => {
  let guard: RecursosGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(RecursosGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
