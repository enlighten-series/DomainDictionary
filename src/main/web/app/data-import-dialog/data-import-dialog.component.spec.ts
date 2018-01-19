import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataImportDialogComponent } from './data-import-dialog.component';

describe('DataImportDialogComponent', () => {
  let component: DataImportDialogComponent;
  let fixture: ComponentFixture<DataImportDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataImportDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataImportDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
