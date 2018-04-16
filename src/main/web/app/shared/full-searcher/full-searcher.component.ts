import { Component, OnInit } from '@angular/core';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';
import { MatSnackBar } from '@angular/material';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Component({
  selector: 'app-full-searcher',
  templateUrl: './full-searcher.component.html',
  styleUrls: ['./full-searcher.component.scss'],
})
export class FullSearcherComponent implements OnInit {
  // #reion インタフェース

  // #endregion

  // #region コンストラクタ・ライフサイクル

  constructor(private snack: MatSnackBar, private http: HttpClient) {}

  ngOnInit() {
    // no-op
  }

  // #endregion

  // #region ビューバインド

  private _emptyResult = [];
  searchResult$ = new BehaviorSubject<any[]>(this._emptyResult);

  isBlockVisible() {
    return this.searchResult$.value.length >= 1;
  }

  // #endregion

  // #region イベント

  public search(keyword: string) {
    this.snack.openFromComponent(GrowlMessagerComponent, {
      data: {
        message: `"${keyword}" で検索します...`,
      },
      duration: 1000,
    });

    if (keyword === '') {
      // 結果を空にする
      this.searchResult$.next(this._emptyResult);
      return;
    }

    const _params = new HttpParams().set('keyword', keyword);

    // 検索実行
    this.http
      .get('/api/domains/_search', {
        params: _params,
      })
      .subscribe(
        (data: any) => {
          this.searchResult$.next(data);
        },
        error => {
          console.log(error);
        },
      );
  }

  // #endregion

  // #region プライベート

  // #endregion
}
