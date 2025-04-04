import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonDirective } from 'src/app/shared/directives/button.directive';
import {
  ConfirmationModalCloseData,
  ConfirmationModalData
} from 'src/app/shared/interfaces/modals/confirmation-modal.interfaces';
import { SafeHtmlPipe } from 'src/app/shared/pipes/pipe.safehtml';
import { ModalBodyComponent } from '../../modal/modal-body/modal-body.component';
import { ModalFooterComponent } from '../../modal/modal-footer/modal-footer.component';
import { ModalSubtitleComponent } from '../../modal/modal-subtitle/modal-subtitle.component';
import { ModalTitleComponent } from '../../modal/modal-title/modal-title.component';
import { ModalComponent } from '../../modal/modal.component';

@Component({
  selector: 'app-confirmation-modal',
  standalone: true,
  imports: [
    CommonModule,
    ButtonDirective,
    ModalBodyComponent,
    ModalComponent,
    ModalFooterComponent,
    ModalSubtitleComponent,
    ModalTitleComponent,
    TranslateModule,
    SafeHtmlPipe
  ],
  templateUrl: './confirmation-modal.component.html'
})
export class ConfirmationModalComponent {
  constructor(
    private dialogRef: MatDialogRef<ConfirmationModalCloseData>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmationModalData
  ) {}

  closeDialog(confirms?: boolean): void {
    this.dialogRef.close({ confirms });
  }
}
