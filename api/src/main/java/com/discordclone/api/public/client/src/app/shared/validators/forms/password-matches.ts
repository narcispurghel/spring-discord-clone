import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const passwordMatchesValidator: ValidatorFn = (
  control: AbstractControl
): ValidationErrors | null => {
  const passwordControl = control.get('password');
  const confirmPasswordControl = control.get('confirmPassword');

  if (passwordControl?.value === confirmPasswordControl?.value) {
    confirmPasswordControl?.setErrors(null);
    return null;
  } else {
    confirmPasswordControl?.setErrors({ passwordsNotMatching: true });
    return { passwordMatch: true };
  }
};
