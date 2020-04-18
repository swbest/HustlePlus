import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AccessRightErrorPage } from './access-right-error.page';

describe('AccessRightErrorPage', () => {
  let component: AccessRightErrorPage;
  let fixture: ComponentFixture<AccessRightErrorPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccessRightErrorPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AccessRightErrorPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
