import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainListRecentComponent } from './domain-list-recent.component';

describe('DomainListRecentComponent', () => {
  let component: DomainListRecentComponent;
  let fixture: ComponentFixture<DomainListRecentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DomainListRecentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DomainListRecentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
