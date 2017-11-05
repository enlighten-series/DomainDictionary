import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatSelectModule,
  MatSidenavModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainDetailComponent } from './domain-detail/domain-detail.component';
import { DomainEditFormComponent, RegistConfirmDialog } from './domain-edit-form/domain-edit-form.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import { LicenseListComponent } from './license-list/license-list.component';

@NgModule({
  declarations: [
    AppComponent,
    DomainListComponent,
    DomainDetailComponent,
    DomainEditFormComponent,
    RegistConfirmDialog,
    DomainCreateComponent,
    LicenseListComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES, {useHash: true}),
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatSidenavModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
  ],
  entryComponents: [
    RegistConfirmDialog,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
