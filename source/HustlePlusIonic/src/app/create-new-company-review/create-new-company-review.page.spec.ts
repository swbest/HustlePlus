import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CreateNewCompanyReviewPage } from './create-new-company-review.page';

describe('CreateNewCompanyReviewPage', () => {
  let component: CreateNewCompanyReviewPage;
  let fixture: ComponentFixture<CreateNewCompanyReviewPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewCompanyReviewPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateNewCompanyReviewPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
