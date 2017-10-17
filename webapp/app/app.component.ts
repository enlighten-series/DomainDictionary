import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  @ViewChild(MatSidenav) sideNav: MatSidenav;

  toggleMenu() {
    this.sideNav.toggle();
  }
}
