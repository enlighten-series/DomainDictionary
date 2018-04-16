import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { LicenseListComponent } from './license-list/license-list.component';
import {
  DataImportDialogComponent,
  ImportConfirmDialogComponent,
} from './data-import-dialog/data-import-dialog.component';
import { DataExportDialogComponent } from './data-export-dialog/data-export-dialog.component';
import { SharedModule } from '../shared/shared.module';
import { DomainListComponent } from './domain-list/domain-list.component';
import { DomainCreateComponent } from './domain-create/domain-create.component';
import {
  DomainEditFormComponent,
  RegistConfirmDialogComponent,
} from './domain-edit-form/domain-edit-form.component';
import {
  DomainDetailComponent,
  DeleteConfirmDialogComponent,
} from './domain-detail/domain-detail.component';
import { EditRelationDialogComponent } from './domain-detail/edit-relation-dialog/edit-relation-dialog.component';

@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterModule,

    CommonModule,
    SharedModule,
  ],
  declarations: [
    DomainListComponent,
    DomainCreateComponent,
    DomainEditFormComponent,
    RegistConfirmDialogComponent,
    DomainDetailComponent,
    DeleteConfirmDialogComponent,
    EditRelationDialogComponent,

    DataExportDialogComponent,
    DataImportDialogComponent,
    ImportConfirmDialogComponent,
    LicenseListComponent,
  ],
  entryComponents: [
    DeleteConfirmDialogComponent,
    RegistConfirmDialogComponent,
    EditRelationDialogComponent,
    DataExportDialogComponent,
    DataImportDialogComponent,
    ImportConfirmDialogComponent,
  ],
})
export class FeaturesModule {}
