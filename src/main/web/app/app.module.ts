import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { ROUTES } from './app.route';

import { AppComponent } from './app.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { FeaturesModule } from './features/features.module';

@NgModule({
  declarations: [
    AppComponent,
    ErrorPageComponent,
    LoginDialogComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(ROUTES, {useHash: true}),

    CoreModule,
    SharedModule,
    FeaturesModule,
  ],
  entryComponents: [
    LoginDialogComponent,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
