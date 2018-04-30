import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { MatSidenav, MatDialog, MatSnackBar } from '@angular/material';
import { Router, NavigationStart } from '@angular/router';
import { DataExportDialogComponent } from './features/data-export-dialog/data-export-dialog.component';
import { DataImportDialogComponent } from './features/data-import-dialog/data-import-dialog.component';
import { LoginDialogComponent } from './shared/login-dialog/login-dialog.component';
import { GrowlMessagerComponent } from './shared/widgets/growl-messager.component';
import { AuthService } from './core/auth/auth.service';
import { Subject } from 'rxjs/Subject';
import { combineLatest } from 'rxjs/observable/combineLatest';
import {
  debounceTime,
  distinctUntilChanged,
  takeUntil,
  merge,
} from 'rxjs/operators';
import { EventEmitter } from 'events';
import { HttpClient, HttpParams } from '@angular/common/http';
import { FullSearcherComponent } from './shared/full-searcher/full-searcher.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  // #reion インタフェース

  // #endregion

  // #region コンストラクタ・ライフサイクル

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private snack: MatSnackBar,
    private auth: AuthService,
  ) {}

  ngOnInit() {
    // 認証が残っている場合継続する
    this.auth.updateAuthentication();

    // メニューからの画面遷移が行われた際にメニューを閉じる
    this.router.events
      .filter(e => e instanceof NavigationStart)
      .subscribe(e => {
        if (this.appMenuVisible) {
          this.appMenuVisible = false;
        }

        this.searchKeyword = '';
      });

    // 検索窓の監視（アプリ全体で１つなので破棄処理なし）
    this.keywordChange$
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        merge(this.searchSubmit$),
      )
      .subscribe((keyword: string) => {
        this.search(keyword);
      });
  }

  // #endregion

  // #region ビューバインド

  @ViewChild(MatSidenav) sideNav: MatSidenav;

  @ViewChild('searcher') searcher: FullSearcherComponent;

  appMenuVisible = false;
  userMenuVisible = false;

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }

  userTooltipLabel() {
    if (this.isAuthenticated()) {
      return this.auth.getAuthData().username;
    } else {
      return 'ログインしていません';
    }
  }

  bindUserButtonColor() {
    if (this.isAuthenticated()) {
      return '';
    }
    return 'accent';
  }

  set searchKeyword(s: string) {
    this._searchKeyword = s;
    this.keywordChange$.next(s);
  }
  get searchKeyword() {
    return this._searchKeyword;
  }

  // #endregion

  // #region イベント

  home() {
    this.router.navigate(['/']);
  }

  toggleAppMenuVisible(e: MouseEvent) {
    this.appMenuVisible = !this.appMenuVisible;
    this.userMenuVisible = false;
    // window:clickでメニューを閉じるイベントまでエスカレーションするのを防止する
    e.stopPropagation();
  }

  showUserDialog(e: MouseEvent) {
    const dialogRef = this.dialog.open(LoginDialogComponent);
  }

  @HostListener('window:click')
  windowClicked(e) {
    this.appMenuVisible = false;
    this.userMenuVisible = false;
  }

  list() {
    this.router.navigate(['/all']);
  }

  export() {
    // エクスポートなので後処理なし
    const dialogRef = this.dialog.open(DataExportDialogComponent);
  }

  import() {
    const dialogRef = this.dialog.open(DataImportDialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        alert('インポートが完了しました。アプリケーションをリロードします。');
        window.location.href = '/';
      }
    });
  }

  license() {
    this.router.navigate(['/license']);
  }

  jumpGithub() {
    window.open('https://github.com/enlighten-series/DomainDictionary');
  }

  onSearch() {
    this.searchSubmit$.next(this._searchKeyword);
  }

  create() {
    this.router.navigate(['/create']);
  }

  // #endregion

  // #region プライベート

  private _searchKeyword = '';
  private keywordChange$ = new Subject<string>();
  private searchSubmit$ = new Subject<string>();

  private search(keyword: string) {
    this.searcher.search(keyword);
  }

  // #endregion
}
