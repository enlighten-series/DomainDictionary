import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { LicenseListComponent } from './license-list/license-list.component';
import { DataImportDialogComponent, ImportConfirmDialog } from './data-import-dialog/data-import-dialog.component';
import { DataExportDialogComponent } from './data-export-dialog/data-export-dialog.component';
import { SharedModule } from '../shared/shared.module';
import { DomainListComponent } from './domain-list/domain-list.component';

@NgModule({
  imports: [
    FormsModule,

    CommonModule,
    SharedModule,
  ],
  declarations: [
    DomainListComponent,
    
    DataExportDialogComponent,
    DataImportDialogComponent,
    ImportConfirmDialog,
    LicenseListComponent,
  ],
  entryComponents: [
    DataExportDialogComponent,
    DataImportDialogComponent,
    ImportConfirmDialog,
  ]
})
export class FeaturesModule { }
