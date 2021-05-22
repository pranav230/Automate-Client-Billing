import { TestBed } from '@angular/core/testing';

import { ServiceClassService } from './service-class.service';

describe('ServiceClassService', () => {
  let service: ServiceClassService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceClassService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
