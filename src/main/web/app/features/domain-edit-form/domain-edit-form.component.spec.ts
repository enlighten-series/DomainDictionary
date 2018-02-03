import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainEditFormComponent } from './domain-edit-form.component';

describe('DomainEditFormComponent', () => {
  let component: DomainEditFormComponent;
  let fixture: ComponentFixture<DomainEditFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DomainEditFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DomainEditFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
