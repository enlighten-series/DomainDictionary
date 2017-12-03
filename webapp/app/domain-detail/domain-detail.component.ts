import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar, MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatTabGroup } from '@angular/material';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Subscription } from 'rxjs/Subscription';
import * as marked from 'marked';

import 'rxjs/add/observable/from';
import 'rxjs/add/operator/first';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/switchMap';

import { Domain } from '../models/domain';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';

@Component({
  selector: 'delete-confirm-dialog',
  template: `
  <mat-dialog-content>
  削除するには、以下にドメイン名<span class="delete-domain-name">{{domainName}}</span>を入力してください。
  <p>
    <mat-form-field>
      <input matInput placeholder="削除するドメイン名"
        [(ngModel)]="enterName"
      >
    </mat-form-field>
  </p>
  </mat-dialog-content>
  <mat-dialog-actions>
    <button mat-raised-button mat-dialog-close>キャンセル</button>
    <button mat-raised-button color="warn" (click)="delete()" [disabled]="deletable()">削除</button>
  </mat-dialog-actions>
  `,
  styles: [
    `.delete-domain-name {
      background-color: #eee;
      border-radius: 3px;
      color: #d00;
      font-size: 0.9em;
      margin: 0 0.1em;
      padding: 0.15em;
    }`,
  ]
})
export class DeleteConfirmDialog {

  domainName: string;
  enterName: string;

  constructor(
    public dialogRef: MatDialogRef<DeleteConfirmDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this.domainName = data.name;
  }

  deletable(): boolean {
    return this.domainName !== this.enterName;
  }

  delete() {
    this.dialogRef.close(true);
  }

}

@Component({
  selector: 'app-domain-detail',
  templateUrl: './domain-detail.component.html',
  styleUrls: ['./domain-detail.component.css']
})
export class DomainDetailComponent implements OnInit {

  @ViewChild(MatTabGroup) matTabGroup: MatTabGroup;

  id: number;
  viewDomain: Domain = new Domain();
  editFormInitialValue: Domain = new Domain();

  private activeIndex = 0;
  private idSubscription: Subscription;

  private parsedName = '';
  private parsedFormat = '';
  private parsedDescription = '';
  private parsedExistential = '';

  constructor(
    private activateRoute: ActivatedRoute,
    private http: HttpClient,
    private snack: MatSnackBar,
    private router: Router,
    private deleteConfirmDialog: MatDialog,
  ) { }

  ngOnInit() {
    // URLでidのみ変更される場合はコンポーネント再作成が行われないため、firstではなく継続的にsubscribeする。
    this.idSubscription = this.activateRoute.paramMap
    .subscribe((param: ParamMap) => {
      this.id = Number(param.get('id'));
      this.load();
    });
  }

  load() {
    this.http.get('/api/domains/' + this.id)
    .subscribe(
      (domain: Domain) => {
        this.viewDomain = domain;
        this.editFormInitialValue = domain;

        // markdown parse
        this.parsedName = this.viewDomain.name;
        marked(this.viewDomain.format, (err, content) => {
          if (err) throw err;
          this.parsedFormat = content;
        });
        marked(this.viewDomain.description, (err, content) => {
          if (err) throw err;
          this.parsedDescription = content;
        });
        marked(this.viewDomain.existential, (err, content) => {
          if (err) throw err;
          this.parsedExistential = content;
        });
      },
      (error) => {
        console.log(error);
      }
    );
  }

  selectedIndexChanged(index) {
    this.activeIndex = index;
  }
  isActiveDetail() {
    return this.activeIndex == 0;
  }
  isActiveEdit() {
    return this.activeIndex == 1;
  }
  isActiveOther() {
    return this.activeIndex == 2;
  }

  innerHtmlOfName() {
    return this.parsedName;
  }
  innerHtmlOfFormat() {
    return this.parsedFormat;
  }
  innerHtmlOfDescription() {
    return this.parsedDescription;
  }
  innerHtmlOfExistential() {
    return this.parsedExistential;
  }

  emittedRegist(event) {
    this.http.put('/api/domains/' + this.id, event)
    .subscribe(
      (data: any) => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ドメインを更新しました',
          },
          duration: 1500,
        });
        this.load();
        this.matTabGroup.selectedIndex = 0;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  clickedDelete() {
    let dialogRef = this.deleteConfirmDialog.open(DeleteConfirmDialog, {
      data: {name: this.viewDomain.name}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.executeDelete();
      }
    });
  }

  executeDelete() {
    this.http.delete('/api/domains/' + this.id)
    .subscribe(
      (data: any) => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ドメインを削除しました',
          },
          duration: 1500,
        });
        this.router.navigate(['/']);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  ngOnDestroy() {
    this.idSubscription.unsubscribe();
  }

}
