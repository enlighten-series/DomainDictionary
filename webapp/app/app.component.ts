import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  @ViewChild(MatSidenav) sideNav: MatSidenav;

  constructor(
    private router: Router
  ) {}

  toggleMenu() {
    this.sideNav.toggle();
  }

  home() {
    this.router.navigate(['/']);
  }
}
