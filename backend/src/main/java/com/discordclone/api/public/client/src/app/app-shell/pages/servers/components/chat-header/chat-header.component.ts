import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { UserAvatarComponent } from 'src/app/shared/components/user-avatar/user-avatar.component';
import { MobileToggleComponent } from './mobile-toggle/mobile-toggle.component';
import { UserDef } from 'src/app/authentication/interfaces/auth.interface';
import { SocketIndicatorComponent } from './socket-indicator/socket-indicator.component';

@Component({
  selector: 'app-chat-header',
  standalone: true,
  imports: [
    CommonModule,
    MobileToggleComponent,
    MatIconModule,
    UserAvatarComponent,
    SocketIndicatorComponent
  ],
  templateUrl: './chat-header.component.html'
})
export class ChatHeaderComponent {
  @Input({ required: true }) type: 'channel' | 'conversation' = 'channel';
  @Input({ required: true }) name: string = '';
  @Input() profile?: UserDef;
  @Input({ required: true }) serverId!: string;
}
