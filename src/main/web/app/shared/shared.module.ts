import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GrowlMessagerComponent } from './widgets/growl-messager.component';

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    GrowlMessagerComponent,
  ],
  entryComponents: [
    GrowlMessagerComponent,
  ]
})
export class SharedModule { }
