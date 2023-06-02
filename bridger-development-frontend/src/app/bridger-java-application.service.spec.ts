import { TestBed } from '@angular/core/testing';

import { BridgerJavaApplicationService } from './bridger-java-application.service';

describe('BridgerJavaApplicationService', () => {
  let service: BridgerJavaApplicationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BridgerJavaApplicationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
