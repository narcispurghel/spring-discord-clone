import { Injectable } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class AuthFormErrorsHandler {
  constructor(private translateService: TranslateService) {}

  getErrorTranslation(control: FormControl): string {
    const errorKey = Object.keys(control.errors ?? {})[0];
    const controlError = control.errors?.[errorKey];
    switch (errorKey) {
      case 'pattern':
        return this.translateService.instant(
          'validators.please-enter-a-valid-email'
        );
      case 'minlength':
        return this.translateService.instant(
          'validators.the-minimum-length-required',
          {
            minLength: controlError.requiredLength
          }
        );
      case 'maxlength':
        return this.translateService.instant(
          'validators.the-maximum-length-allowed',
          { maxLength: controlError.requiredLength }
        );
      case 'passwordsNotMatching':
        return this.translateService.instant(
          'validators.passwords-do-not-match'
        );
      default:
        return this.translateService.instant(
          'validators.this-field-is-required'
        );
    }
  }
}
