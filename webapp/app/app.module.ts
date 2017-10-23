import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  MatButtonModule,
  MatCardModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatSidenavModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainDetailComponent } from './domain-detail/domain-detail.component';
import { DomainEditFormComponent } from './domain-edit-form/domain-edit-form.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';

@NgModule({
  declarations: [
    AppComponent,
    DomainListComponent,
    DomainDetailComponent,
    DomainEditFormComponent,
    DomainCreateComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule.forRoot(ROUTES),
    MatButtonModule,
    MatCardModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
