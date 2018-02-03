import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { Observable } from 'rxjs/Observable';
import { startWith, map } from 'rxjs/operators';

import { DomainSummary } from '../../../models/domain-summary';
import { RelatedDomain } from '../../../models/related-domain';
import { GrowlMessagerComponent } from '../../../shared/widgets/growl-messager.component';

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
    public snack: MatSnackBar,
  ) {
    this.setupFilter();

    this.sourceId = this.data.sourceId;
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
  relatedDomains: RelatedDomain[] = [];
  
  displayFn(option: DomainSummary) {
    return option ? option.name : option;
  }

  // #endregion

  // #region event

  remove(domain: RelatedDomain): void {
    let index = this.relatedDomains.indexOf(domain);

    if (index >= 0) {
      this.http.delete('/api/relations/' + domain.relationId)
      .subscribe(
        (data: any) => {
          this.relatedDomains.splice(index, 1);
          this.snack.openFromComponent(GrowlMessagerComponent, {
            data: {
              message: '関連を削除しました',
            },
            duration: 800,
          });
        },
        (error: HttpErrorResponse) => {
          this.snack.openFromComponent(GrowlMessagerComponent, {
            data: {
              message: error.error.message,
            },
            duration: 3000,
          });
        }
      );
    }
  }

  onSubmit() {
    if (!this.domainAddFormGroup.valid) {
      return false;
    }

    const _formValue = this.domainAddFormGroup.value['addDomain'];
    let targetDomain = null;
    if (typeof _formValue === 'string') {
      for (let i = 0; i < this.domainSummaryOptions.length; i++) {
        if (this.domainSummaryOptions[i].name === _formValue) {
          targetDomain = this.domainSummaryOptions[i];
          break;
        }
      }
    } else {
      targetDomain = _formValue;
    }

    if (targetDomain == null) {
      alert('該当する項目が見つかりません');
      return;
    }
    if (targetDomain.id == this.sourceId) {
      alert('同一ドメインを関連づけることはできません');
      this.domainAddFormGroup.reset();
      return;
    }

    this.http.post('/api/relations', {
      source: this.sourceId,
      destination: targetDomain.id,
    })
    .subscribe(
      (data: any) => {
        this.relatedDomains.push(new RelatedDomain(
          data.id,
          targetDomain.id,
          targetDomain.name,
        ));
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: '関連を追加しました',
          },
          duration: 800,
        });
      },
      (error) => {
        alert('エラーがありました。コンソールログを確認してください。');
        console.log(error);
      }
    );

    // 送信したらフォームはクリア
    this.domainAddFormGroup.reset();
  }

  // #endregion

  // #region private

  private domainSummaryOptions: DomainSummary[] = [];
  private sourceId: number;

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
        (data: DomainSummary[]) => {
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
