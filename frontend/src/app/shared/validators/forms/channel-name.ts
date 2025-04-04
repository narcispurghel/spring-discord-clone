import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const forbiddenChannelNames = ['general'];

export function channelName(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null =>
    forbiddenChannelNames.includes(
      ((control.value || '') as string).trim().toLowerCase()
    )
      ? { forbiddenName: { value: control.value } }
      : null;
}
