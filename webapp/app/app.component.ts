import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { Router, NavigationStart } from '@angular/router';

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
    private router: Router
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

  toggleMenu() {
    this.sideNav.toggle();
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

  license() {
    this.router.navigate(['/license']);
  }

  login() {
    alert('login!');
  }
}
