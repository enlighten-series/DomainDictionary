import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';

@Component({
  selector: 'app-data-import-dialog',
  templateUrl: './data-import-dialog.component.html',
  styleUrls: ['./data-import-dialog.component.scss']
})
export class DataImportDialogComponent implements OnInit {

  // #reion インタフェース

  // #endregion

  // #region コンストラクタ・ライフサイクル

  constructor(
    public dialogRef: MatDialogRef<DataImportDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,
    private snack: MatSnackBar,
  ) { }

  ngOnInit() {
  }

  // #endregion

  // #region ビューバインド

  @ViewChild('form') form: NgForm;

  importFile: any;

  getLatestImportDate() {
    return new Date();
  }

  // #endregion

  // #region イベント

  importFileChange(event) {
    const fileList: FileList = event.target.files;
    if(fileList.length > 0) {
      this.selectedImportFile = fileList[0];
    }
  }

  import() {
    const formData: FormData = new FormData();
    formData.append('importFile', this.selectedImportFile, this.selectedImportFile.name);

    const headers = new HttpHeaders();
    headers.set('Accept', 'application/json');

    this.http.post('/api/application-migration/import', formData, {
      headers: headers,
    })
    .subscribe(
      data => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'インポートが完了しました。',
          },
          duration: 3000,
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
    this.form.reset();
  }

  // #endregion

  // #region プライベート

  private selectedImportFile: File;

  // #endregion

}
