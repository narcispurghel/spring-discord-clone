import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { debounceTime, filter } from 'rxjs';
import { ChannelDef } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { CHANNEL_ICON_MAP } from 'src/app/shared/constants/channel-icon-map.constant';
import { ModalService } from 'src/app/shared/services/modal.service';
import { ChannelModalData } from '../../../interfaces/channel-modal.interface';
import { ServerWithChannelsMembersAndProfilesDef } from '../../../interfaces/servers.interfaces';
import { ServersFacade } from '../../../store/servers.facade';
import { ChannelModalComponent } from '../../channel-modal/channel-modal.component';
import { SidenavService } from 'src/app/app-shell/services/sidenav.service';

@Component({
  selector: 'app-server-channel',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatTooltipModule, TranslateModule],
  templateUrl: './server-channel.component.html'
})
export class ServerChannelComponent {
  @Input({ required: true }) channel!: ChannelDef;
  @Input({ required: true }) server!: ServerWithChannelsMembersAndProfilesDef;
  @Input() role: MemberRole | undefined;

  private router = inject(Router);
  private modalServer = inject(ModalService);
  private serversFacade = inject(ServersFacade);
  private translateService = inject(TranslateService);
  private sidenavService = inject(SidenavService);

  activeChannelId$ = this.serversFacade
    .getActiveChannelId$()
    .pipe(debounceTime(0));
  channelIconMap = CHANNEL_ICON_MAP;
  roles = MemberRole;

  onClick(): void {
    this.router.navigate([
      'servers',
      this.server.id,
      'channels',
      this.channel.id
    ]);
    this.sidenavService.closeIfOpen();
  }

  onAction(e: MouseEvent, action: 'delete-channel' | 'edit-channel') {
    e.stopPropagation();
    action === 'edit-channel'
      ? this.modalServer.openRegular<ChannelModalComponent, ChannelModalData>(
          ChannelModalComponent,
          { channel: this.channel }
        )
      : this.handleDeletion();
  }

  private handleDeletion(): void {
    this.modalServer
      .openConfirmationModal({
        message: this.translateService.instant(
          'content.channel-deletion-message',
          { channelName: this.channel.name }
        )
      })
      .afterClosed()
      .pipe(filter((response) => !!response?.confirms))
      .subscribe(() => this.serversFacade.deleteChannel(this.channel.id));
  }
}
