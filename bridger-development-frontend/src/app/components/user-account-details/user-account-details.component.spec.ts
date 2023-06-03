import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BridgerDetailsComponent } from './user-account-details.component';

describe('BridgerDetailsComponent', () => {
  let component: BridgerDetailsComponent;
  let fixture: ComponentFixture<BridgerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BridgerDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BridgerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
