import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './modal.component.html'
})
export class ModalComponent {
  constructor(public dialogRef: MatDialogRef<any>) {}

  @Input() showActions = true;

  closeDialog() {
    this.dialogRef.close('Pizza!');
  }
}
