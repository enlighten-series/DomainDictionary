import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { MatSidenav, MatDialog } from '@angular/material';
import { Router, NavigationStart } from '@angular/router';
import { DataExportDialogComponent } from './data-export-dialog/data-export-dialog.component';
import { DataImportDialogComponent } from './data-import-dialog/data-import-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  
  @ViewChild(MatSidenav) sideNav: MatSidenav;

  appMenuVisible = false;
  userMenuVisible = false;

  constructor(
    private router: Router,
    private dialog: MatDialog,
  ) {}

  ngOnInit() {
    // メニューからの画面遷移が行われた際にメニューを閉じる
    this.router.events
      .filter((e) => e instanceof NavigationStart)
      .subscribe((e) => {
        if (this.appMenuVisible) {
          this.appMenuVisible = false;
        }
      });
  }

  home() {
    this.router.navigate(['/']);
  }

  toggleAppMenuVisible(e: MouseEvent) {
    this.appMenuVisible = !this.appMenuVisible;
    this.userMenuVisible = false;
    // window:clickでメニューを閉じるイベントまでエスカレーションするのを防止する
    e.stopPropagation();
  }

  toggleUserMenuVisible(e: MouseEvent) {
    this.userMenuVisible = !this.userMenuVisible;
    this.appMenuVisible = false;
    // window:clickでメニューを閉じるイベントまでエスカレーションするのを防止する
    e.stopPropagation();
  }

  @HostListener('window:click')
  windowClicked(e) {
    this.appMenuVisible = false;
    this.userMenuVisible = false;
  }

  export() {
    // エクスポートなので後処理なし
    let dialogRef = this.dialog.open(DataExportDialogComponent);
  }

  import() {
    let dialogRef = this.dialog.open(DataImportDialogComponent);
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

  login() {
    alert('login!');
  }

  jumpGithub() {
    window.open('https://github.com/enlighten-series/DomainDictionary');
  }
}
