import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {
  MatButtonModule,
  MatIconModule,
  MatSidenavModule,
  MatToolbarModule,
} from '@angular/material';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { DomainListComponent } from './domain-list/domain-list.component';

@NgModule({
  declarations: [
    AppComponent,
    DomainListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(ROUTES),
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
