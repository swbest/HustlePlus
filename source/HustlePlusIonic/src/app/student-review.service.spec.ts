import { TestBed } from '@angular/core/testing';

import { StudentReviewService } from './student-review.service';

describe('StudentReviewService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StudentReviewService = TestBed.get(StudentReviewService);
    expect(service).toBeTruthy();
  });
});
