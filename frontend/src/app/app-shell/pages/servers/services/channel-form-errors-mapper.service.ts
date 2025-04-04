import { Injectable } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

@Injectable()
export class ChannelFormErrorsHandler {
  constructor(private translateService: TranslateService) {}

  getErrorTranslation(control: FormControl): string {
    const errorKey = Object.keys(control.errors ?? {})[0];
    const controlError = control.errors?.[errorKey];
    switch (errorKey) {
      case 'forbiddenName':
        return this.translateService.instant(
          'validators.channel-name-cannot-be-value',
          { value: controlError.value }
        );
      default:
        return this.translateService.instant(
          'validators.this-field-is-required'
        );
    }
  }
}
