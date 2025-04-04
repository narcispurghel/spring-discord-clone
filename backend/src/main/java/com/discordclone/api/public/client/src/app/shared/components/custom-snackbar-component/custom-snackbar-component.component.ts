import { Component, Inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import {
  SnackbarData,
  SnackbarMessageType
} from '../../interfaces/snackbar.interface';

const SnackbarTypeLiterals: {
  [key in SnackbarMessageType]: { iconClass: string; iconType: string };
} = {
  info: { iconClass: '', iconType: '' },
  success: {
    iconClass: 'mr-2 text-green-500',
    iconType: 'check_circle_outline'
  },
  warning: { iconClass: 'mr-2 text-red-500', iconType: 'error_outline' }
};

@Component({
  selector: 'app-custom-snackbar-component',
  templateUrl: './custom-snackbar-component.component.html',
  styleUrls: ['./custom-snackbar-component.component.scss'],
  standalone: true,
  imports: [MatIconModule]
})
export class CustomSnackbarComponentComponent {
  iconClass!: string;
  iconType!: string;

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: SnackbarData) {
    const mappedData = SnackbarTypeLiterals[this.data.type];
    this.iconClass = mappedData.iconClass;
    this.iconType = mappedData.iconType;
  }
}
