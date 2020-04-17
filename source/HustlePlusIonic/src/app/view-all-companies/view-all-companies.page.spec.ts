import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ViewAllCompaniesPage } from './view-all-companies.page';

describe('ViewAllCompaniesPage', () => {
  let component: ViewAllCompaniesPage;
  let fixture: ComponentFixture<ViewAllCompaniesPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllCompaniesPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ViewAllCompaniesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
