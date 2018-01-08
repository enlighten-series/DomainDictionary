import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import 'rxjs/add/observable/throw';

@Component({
  selector: 'app-data-export-dialog',
  templateUrl: './data-export-dialog.component.html',
  styleUrls: ['./data-export-dialog.component.scss']
})
export class DataExportDialogComponent implements OnInit {

  // #reion インタフェース

  // #endregion

  // #region コンストラクタ・ライフサイクル

  constructor(
    public dialogRef: MatDialogRef<DataExportDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,
  ) { }

  ngOnInit() {
    this.checkGeneratingStatus();
  }

  // #endregion

  // #region ビューバインド

  isNowGenerating(): boolean {
    return this.nowGenerating;
  }

  getLatestGeneratedDate(): Date {
    return this.latestGeneratedDate;
  }

  isEnableDownload(): boolean {
    return !this.nowGenerating && this.generated;
  }

  // #endregion

  // #region イベント

  generateExportFile() {
    this.nowGenerating = true;
    this.http.post('/api/application-migration/export/generate', null)
    .subscribe(
      (res: any) => {
        console.log('trigger start!');
        this.triggerCycleCheckStatus();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  downloadExportFile() {
    window.location.href = '/api/application-migration/export/download';
  }

  // #endregion

  // #region プライベート

  private nowGenerating: boolean = false;
  private generated: boolean = false;
  private latestGeneratedDate: Date;

  private checkGeneratingStatus() {
    this.http.get('/api/application-migration/export/status')
    .subscribe(
      (status: any) => {
        this.nowGenerating = status.nowGenerating;
        this.latestGeneratedDate = status.latestGeneratedDate;
        if (this.latestGeneratedDate) {
          this.generated = true;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
  
  private triggerCycleCheckStatus() {
    console.log('trigger');
    setTimeout(() => {
      console.log('check');
      this.checkGeneratingStatus();
      if (this.nowGenerating) {
        this.triggerCycleCheckStatus();
      }
    }, 1000);
  }

  // #endregion

}
