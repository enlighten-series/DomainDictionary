import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

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
  ) { }

  ngOnInit() {
    // TODO: エクスポートファイルの生成状態をリクエスト
    this.nowGenerating = false;
    this.generated = true;
    this.latestGeneratedDate = new Date('2017/12/31 23:59:59');
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
    // TODO: エクスポートファイルの生成開始リクエスト
  }

  downloadExportFile() {
    // TODO: エクスポートファイルのダウンロードリクエスト
  }

  // #endregion

  // #region プライベート

  nowGenerating: boolean = false;
  generated: boolean = false;
  latestGeneratedDate: Date;

  // #endregion

}
