import { TestBed } from '@angular/core/testing';

import { TokenService } from './token.service';

describe('TokenService', () => {
  let service: TokenService;

  const TOKEN_HASH = "hash123";
  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.get(TokenService);
  });

  it('should be created', () => {    
    expect(service).toBeTruthy();
  });

  it('should store the token and retrieve the token', (done: DoneFn) => {
    service.configurarTokenAutenticacao(TOKEN_HASH).subscribe(() => {
      done();
    });
  });

  it('should store the token and retrieve the token', (done: DoneFn) => {
    service.obterTokenAutenticacao().subscribe(t => {
      expect(t.hash).toEqual(TOKEN_HASH);
      expect(t.dataGeracao < new Date());
      done();
    });
  });
});
