import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BridgerListComponent } from './user-account-list.component';

describe('BridgerListComponent', () => {
  let component: BridgerListComponent;
  let fixture: ComponentFixture<BridgerListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BridgerListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BridgerListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
