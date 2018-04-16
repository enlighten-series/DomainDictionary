import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FullSearcherComponent } from './full-searcher.component';

describe('FullSearcherComponent', () => {
  let component: FullSearcherComponent;
  let fixture: ComponentFixture<FullSearcherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FullSearcherComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FullSearcherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
