import {Component, Inject} from '@angular/core';
import {MAT_SNACK_BAR_DATA} from '@angular/material';

@Component({
  selector: 'app-growl-messager',
  template: '{{ data.message }}',
})
export class GrowlMessagerComponent {
  constructor(
    @Inject(MAT_SNACK_BAR_DATA) public data: any
  ) { }
}
