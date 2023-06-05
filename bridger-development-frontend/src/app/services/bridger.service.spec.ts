import { TestBed } from '@angular/core/testing';

import { BridgerService } from './bridger.service';

describe('BridgerService', () => {
  let service: BridgerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BridgerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
