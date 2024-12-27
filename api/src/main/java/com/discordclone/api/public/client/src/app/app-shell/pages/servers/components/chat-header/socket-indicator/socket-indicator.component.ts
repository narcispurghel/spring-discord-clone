import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  OnDestroy,
  OnInit,
  inject,
  signal
} from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Socket } from 'ngx-socket-io';
import { SocketStatus } from '../../../services/socket-status.service';

@Component({
  selector: 'app-socket-indicator',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './socket-indicator.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: `
    :host {
      display: block;
    }
  `
})
export class SocketIndicatorComponent {
  isConnected = inject(SocketStatus).isConnected;
}
