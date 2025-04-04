import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

import { CustomSnackbarComponentComponent } from '../components/custom-snackbar-component/custom-snackbar-component.component';
import {
  SnackbarData,
  SnackbarMessage,
  SnackbarMessageType
} from '../interfaces/snackbar.interface';

@Injectable({
  providedIn: 'root'
})
export class SnackBarService {
  constructor(
    private snackBar: MatSnackBar,
    private translateService: TranslateService
  ) {}

  openSnackBar(
    message: SnackbarMessage,
    type: SnackbarMessageType = 'info',
    duration = 4000
  ) {
    this.snackBar.openFromComponent(CustomSnackbarComponentComponent, {
      data: this.mapToSnackbarData(message, type),
      duration,
      horizontalPosition: 'right',
      verticalPosition: 'bottom'
    });
  }

  private mapToSnackbarData(
    message: SnackbarMessage,
    type: SnackbarMessageType
  ): SnackbarData {
    return {
      message:
        typeof message === 'object'
          ? this.translateService.instant(
              message.messageKey,
              message.interpolateparams
            )
          : this.translateService.instant(message),
      type
    };
  }
}
