import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function noWhiteSpace(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null =>
    (control.value || '').trim().length ? null : { whitespace: true };
}
