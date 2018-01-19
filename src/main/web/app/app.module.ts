import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCardModule,
  MatChipsModule,
  MatDialogModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatSelectModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
} from '@angular/material';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainDetailComponent, DeleteConfirmDialog } from './domain-detail/domain-detail.component';
import { DomainEditFormComponent, RegistConfirmDialog } from './domain-edit-form/domain-edit-form.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import { LicenseListComponent } from './license-list/license-list.component';
import { GrowlMessagerComponent } from './widgets/growl-messager.component';
import { HttpStatusInterceptor } from './services/http-status-interceptor';
import { ErrorPageComponent } from './error-page/error-page.component';
import { DataExportDialogComponent } from './data-export-dialog/data-export-dialog.component';
import { EditRelationDialogComponent } from './domain-detail/edit-relation-dialog/edit-relation-dialog.component';
import { DataImportDialogComponent, ImportConfirmDialog } from './data-import-dialog/data-import-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DomainListComponent,
    DomainDetailComponent,
    DeleteConfirmDialog,
    DomainEditFormComponent,
    RegistConfirmDialog,
    DomainCreateComponent,
    LicenseListComponent,
    GrowlMessagerComponent,
    ErrorPageComponent,
    DataExportDialogComponent,
    EditRelationDialogComponent,
    DataImportDialogComponent,
    ImportConfirmDialog,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES, {useHash: true}),
    MatAutocompleteModule,
    MatButtonModule,
    MatCardModule,
    MatChipsModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatSidenavModule,
    MatSnackBarModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
  ],
  entryComponents: [
    DataExportDialogComponent,
    DataImportDialogComponent,
    DeleteConfirmDialog,
    RegistConfirmDialog,
    GrowlMessagerComponent,
    EditRelationDialogComponent,
    ImportConfirmDialog,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpStatusInterceptor,
    multi: true,
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }