import { Directive, forwardRef, Attribute, Input } from '@angular/core';
import { Validator, AbstractControl, NG_VALIDATORS } from '@angular/forms';

@Directive({
  selector: '[validateEqual][ngModel]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => EqualValidator),
      multi: true
    }
  ]
})
export class EqualValidator implements Validator {

  @Input('validateEqual') public validateEqual: any;

  constructor(
  ) {
  }

  validate(c: AbstractControl): { [key: string]: any } {

    // value not equal
    if (c.value !== this.validateEqual) {
      return {
        validateEqual: false
      }
    }

    return null;
  }
}