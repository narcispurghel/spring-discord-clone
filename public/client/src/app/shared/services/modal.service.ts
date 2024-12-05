import { ComponentType } from '@angular/cdk/portal';
import { Injectable, TemplateRef } from '@angular/core';
import {
  MatDialog,
  MatDialogConfig,
  MatDialogRef
} from '@angular/material/dialog';
import {
  ConfirmationModalCloseData,
  ConfirmationModalData
} from '../interfaces/modals/confirmation-modal.interfaces';
import { ConfirmationModalComponent } from '../components/modals/confirmation-modal/confirmation-modal.component';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  constructor(private dialog: MatDialog) {}

  openRegular<T, D = any, R = any>(
    componentOrTemplateRef: ComponentType<T> | TemplateRef<T>,
    data?: D
  ): MatDialogRef<T, R> {
    return this.dialog.open(componentOrTemplateRef, { data });
  }

  openConfirmationModal(
    data: ConfirmationModalData
  ): MatDialogRef<ConfirmationModalComponent, ConfirmationModalCloseData> {
    return this.dialog.open(ConfirmationModalComponent, { data });
  }
}
