import { TestBed } from '@angular/core/testing';

import { DossierService } from './dossier.service';
import {HttpClientModule} from '@angular/common/http';

describe('DossierService', () => {
  let service: DossierService;

  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientModule]
    });
    service = TestBed.inject(DossierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
