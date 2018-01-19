import { Component, OnInit, Input, Output, EventEmitter, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NgForm } from '@angular/forms';
import { from } from 'rxjs/observable/from';

import { Domain } from '../models/domain';

@Component({
  selector: 'regist-confirm-dialog',
  template: `
  <mat-dialog-content>登録しますか？</mat-dialog-content>
  <mat-dialog-actions>
    <button mat-raised-button mat-dialog-close>No</button>
    <button mat-raised-button color="primary" [mat-dialog-close]="true">Yes</button>
  </mat-dialog-actions>
  `,
})
export class RegistConfirmDialog {

  constructor(
    public dialogRef: MatDialogRef<RegistConfirmDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

}

@Component({
  selector: 'app-domain-edit-form',
  templateUrl: './domain-edit-form.component.html',
  styleUrls: ['./domain-edit-form.component.css'],
})
export class DomainEditFormComponent implements OnInit {

  @Input()
  set initialValue(d: Domain) {
    this.editingDomain.name = d.name;
    this.editingDomain.format = d.format;
    this.editingDomain.description = d.description;
    this.editingDomain.existential = d.existential;
  }

  @Output() regist = new EventEmitter<any>();

  editingDomain: any = {
    name: '',
    format: '',
    description: '',
    existential: '',
  };

  constructor(
    private registConfirmDialog: MatDialog,
  ) { }

  ngOnInit() {
  }

  clickedRegist(form: NgForm) {
    if (!form.valid) {
      // 入力項目をdirtyにしてエラーメッセージを表示する
      from(Object.keys(form.controls))
      .subscribe(
        key => {
          form.controls[key].markAsDirty();
        }
      )
      return;
    }

    let dialogRef = this.registConfirmDialog.open(RegistConfirmDialog);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.regist.emit(this.editingDomain);
      }
    });
  }

}
