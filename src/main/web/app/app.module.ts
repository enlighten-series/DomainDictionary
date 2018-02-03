import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainDetailComponent, DeleteConfirmDialog } from './domain-detail/domain-detail.component';
import { DomainEditFormComponent, RegistConfirmDialog } from './domain-edit-form/domain-edit-form.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { EditRelationDialogComponent } from './domain-detail/edit-relation-dialog/edit-relation-dialog.component';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { FeaturesModule } from './features/features.module';

@NgModule({
  declarations: [
    AppComponent,
    DomainListComponent,
    DomainDetailComponent,
    DeleteConfirmDialog,
    DomainEditFormComponent,
    RegistConfirmDialog,
    DomainCreateComponent,
    ErrorPageComponent,
    EditRelationDialogComponent,
    LoginDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES, {useHash: true}),

    CoreModule,
    SharedModule,
    FeaturesModule,
  ],
  entryComponents: [
    DeleteConfirmDialog,
    RegistConfirmDialog,
    EditRelationDialogComponent,
    LoginDialogComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
