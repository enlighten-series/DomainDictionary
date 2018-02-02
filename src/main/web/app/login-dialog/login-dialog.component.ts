import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatSnackBar, MatTabGroup } from '@angular/material';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';
import { AuthService } from '../core/auth/auth.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss'],
})
export class LoginDialogComponent implements OnInit {

  // #reion インタフェース

  // #endregion

  // #region コンストラクタ・ライフサイクル

  constructor(
    public dialogRef: MatDialogRef<LoginDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,
    private snack: MatSnackBar,
    private auth: AuthService,
    private router: Router,
  ) { }

  ngOnInit() {
  }

  // #endregion

  // #region ビューバインド

  @ViewChild(MatTabGroup) matTabGroup: MatTabGroup;

  username: string;
  password: string;

  signupUsername: string;
  signupPassword: string;
  signupPasswordConfirm: string;

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }

  isActiveAuth() {
    return this.activeIndex == 0;
  }
  isActiveSignUp() {
    return this.activeIndex == 1;
  }

  getAuthUserName(): string {
    if (this.isAuthenticated()) {
      return this.auth.getAuthData().username;
    } else {
      return '';
    }
  }

  // #endregion

  // #region イベント

  selectedIndexChanged(index) {
    this.activeIndex = index;
  }

  login(form: NgForm) {
    if (form.invalid) {
      return;
    }

    const data = 'username=' + encodeURIComponent(this.username) +
      '&password=' + encodeURIComponent(this.password);
    const headers = new HttpHeaders ({
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    /**
     * TODO: このへん治ったらerrorとして受け取りたい
     * https://github.com/angular/angular/issues/19555
     * https://github.com/angular/angular/issues/18680
     */
    this.http.post('/api/login', data, { headers: headers })
    .subscribe(
      data => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ログインしました',
          },
          duration: 3000,
        });
        this.auth.updateAuthentication();
        this.dialogRef.close(true);
      }
    );
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
        this.auth.clearAuthentication();
        this.router.navigate(['/']);
      }
    );
  }

  signup(form: NgForm) {
    if (form.invalid) {
      return;
    }

    this.http.post('/api/user', {
      username: this.signupUsername,
      password: this.signupPassword,
    })
    .subscribe(
      data => {
        this.snack.openFromComponent(GrowlMessagerComponent, {
          data: {
            message: 'ユーザ:' + this.signupUsername + 'が作成されました！',
          },
          duration: 3000,
        });
        form.reset();
        this.matTabGroup.selectedIndex = 0;
      }
    );
  }

  // #endregion

  // #region プライベート

  private activeIndex = 0;

  // #endregion

}
