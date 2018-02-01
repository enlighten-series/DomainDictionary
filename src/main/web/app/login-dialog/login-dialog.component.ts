import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA, MatSnackBar } from '@angular/material';
import { GrowlMessagerComponent } from '../widgets/growl-messager.component';
import { AuthService } from '../core/auth/auth.service';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
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

  username: string;
  password: string;

  isAuthenticated() {
    return this.auth.isAuthenticated();
  }

  // #endregion

  // #region イベント

  login(loginForm: NgForm) {
    if (!loginForm.valid) {
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

  // #endregion

  // #region プライベート

  // #endregion

}
