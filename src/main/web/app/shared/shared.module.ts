import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GrowlMessagerComponent } from './widgets/growl-messager.component';
import { EqualValidatorDirective } from './directives/equal-validator.directive';

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    GrowlMessagerComponent,
    EqualValidatorDirective,
  ],
  entryComponents: [
    GrowlMessagerComponent,
  ],
  exports: [
    EqualValidatorDirective,
  ]
})
export class SharedModule { }
