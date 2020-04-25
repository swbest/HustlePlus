import { TestBed } from '@angular/core/testing';

import { CompanyReviewService } from './company-review.service';

describe('CompanyReviewService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CompanyReviewService = TestBed.get(CompanyReviewService);
    expect(service).toBeTruthy();
  });
});
