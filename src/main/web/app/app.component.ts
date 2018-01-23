import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { MatSidenav, MatDialog, MatSnackBar } from '@angular/material';
import { Router, NavigationStart } from '@angular/router';
import { DataExportDialogComponent } from './data-export-dialog/data-export-dialog.component';
import { DataImportDialogComponent } from './data-import-dialog/data-import-dialog.component';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { GrowlMessagerComponent } from './widgets/growl-messager.component';
import { AuthService } from './core/auth/auth.service';

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
    private http: HttpClient,
    private snack: MatSnackBar,
    private auth: AuthService,
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
    let dialogRef = this.dialog.open(LoginDialogComponent);
  }

  logout() {
    this.http.post('/api/logout', {})
    .subscribe(
      data => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ログアウトしました',
          },
          duration: 3000,
        });
      }
    );
  }

  isAuthenticated() {
    this.auth.isAuthenticated();
  }

  jumpGithub() {
    window.open('https://github.com/enlighten-series/DomainDictionary');
  }
}
