import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRelationDialogComponent } from './edit-relation-dialog.component';

describe('EditRelationDialogComponent', () => {
  let component: EditRelationDialogComponent;
  let fixture: ComponentFixture<EditRelationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditRelationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditRelationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
