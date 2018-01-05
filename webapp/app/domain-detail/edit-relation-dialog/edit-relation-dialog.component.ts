import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';

import { DomainSummary } from '../../models/domain-summary';
import {  } from '@angular/forms/src/model';

@Component({
  selector: 'app-edit-relation-dialog',
  templateUrl: './edit-relation-dialog.component.html',
  styleUrls: ['./edit-relation-dialog.component.scss']
})
export class EditRelationDialogComponent implements OnInit {

  // #reion interface

  // #endregion

  // #region constructor/lifecycle hook

  constructor(
    public dialogRef: MatDialogRef<EditRelationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,
  ) {
    this.setupFilter();

    this.relatedDomains = this.data.currentRelatedDomains.slice();
  }

  ngOnInit() {
    this.requestAllDomainSummary();
  }

  // #endregion

  // #region view bind

  domainAddFormGroup = new FormGroup({
    addDomain: new FormControl(),
  });

  filteredOptions$: Observable<DomainSummary[]>;
  relatedDomains: DomainSummary[] = [];
  
  displayFn(option: DomainSummary) {
    return option ? option.name : option;
  }

  // #endregion

  // #region event

  remove(domain: any): void {
    let index = this.relatedDomains.indexOf(domain);

    if (index >= 0) {
      this.relatedDomains.splice(index, 1);
    }
  }

  onSubmit() {
    if (this.domainAddFormGroup.valid) {
      console.log('submit');
      console.log(this.domainAddFormGroup.value);
    }
  }

  // #endregion

  // #region private

  private domainSummaryOptions = [];

  private setupFilter() {
    this.filteredOptions$ = this.domainAddFormGroup.controls['addDomain'].valueChanges
      .pipe(
        startWith({} as DomainSummary),
        map((input: string|DomainSummary) => input && typeof input === 'object' ? input.name : input),
        map((input: string) => input ? this.filter(input) : []),
      );
  }

  private requestAllDomainSummary() {
    this.http.get('/api/domains')
      .subscribe(
        (data: any) => {
          this.domainSummaryOptions = data;
        },
        (error) => {
          console.log(error);
          this.domainSummaryOptions = [];
        }
      );
  }
  
  private filter(name: string): DomainSummary[] {
    return this.domainSummaryOptions.filter(option =>
      option.name.indexOf(name) >= 0);
  }

  // #endregion

}
