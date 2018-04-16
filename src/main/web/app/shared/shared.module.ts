import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
  MatTooltipModule,
} from '@angular/material';

import { GrowlMessagerComponent } from './widgets/growl-messager.component';
import { EqualValidatorDirective } from './directives/equal-validator.directive';
import { ErrorPageComponent } from './error-page/error-page.component';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { FormsModule } from '@angular/forms';
import { FullSearcherComponent } from './full-searcher/full-searcher.component';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    FormsModule,
    RouterModule,
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
    MatTooltipModule,

    CommonModule,
  ],
  declarations: [
    GrowlMessagerComponent,
    ErrorPageComponent,
    LoginDialogComponent,
    EqualValidatorDirective,
    FullSearcherComponent,
  ],
  entryComponents: [GrowlMessagerComponent, LoginDialogComponent],
  exports: [
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
    MatTooltipModule,

    EqualValidatorDirective,
    FullSearcherComponent,
  ],
})
export class SharedModule {}
