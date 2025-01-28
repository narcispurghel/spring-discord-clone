import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { filter } from 'rxjs';
import { ServerModalComponent } from 'src/app/app-shell/components/server-modal/server-modal.component';
import { CreateEditServerModalData } from 'src/app/app-shell/interfaces/create-edit-server.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { ServerDef } from 'src/app/app-shell/interfaces/server.interface';
import { AppShellFacade } from 'src/app/app-shell/store/app-shell.facade';
import { ModalService } from 'src/app/shared/services/modal.service';
import {
  ServerHeaderOptionType,
  serverHeaderOptions
} from '../../../constants/server-header.constants';
import { ServerWithChannelsMembersAndProfilesDef } from '../../../interfaces/servers.interfaces';
import { ChannelModalComponent } from '../../channel-modal/channel-modal.component';
import { InviteFriendsModalComponent } from '../../invite-friends-modal/invite-friends-modal.component';
import { ManageMembersModalComponent } from '../../manage-members-modal/manage-members-modal.component';

@Component({
  selector: 'app-server-header',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatMenuModule,
    TranslateModule,
    MatDividerModule
  ],
  templateUrl: './server-header.component.html'
})
export class ServerHeaderComponent {
  @Input({ required: true }) server!: ServerWithChannelsMembersAndProfilesDef;
  @Input({ required: true }) role!: MemberRole;

  private modalService = inject(ModalService);
  private shellFacade = inject(AppShellFacade);
  private translateService = inject(TranslateService);

  headerOptions = serverHeaderOptions;

  canDisplayOption(canAccessRoles: MemberRole[]): boolean {
    return canAccessRoles.includes(this.role);
  }

  onMenuItemClicked(type: ServerHeaderOptionType): void {
    switch (type) {
      case 'invite-people':
        this.modalService.openRegular(InviteFriendsModalComponent);
        break;
      case 'server-settings':
        this.openServerSettings();
        break;
      case 'manage-members':
        this.modalService.openRegular(ManageMembersModalComponent);
        break;
      case 'create-channel':
        this.modalService.openRegular(ChannelModalComponent);
        break;
      case 'delete-server':
        this.handleServerDeletion();
        break;
      case 'leave-server':
        this.handleLeaveServer();
        break;
    }
  }

  private openServerSettings(): void {
    this.modalService.openRegular<
      ServerModalComponent,
      CreateEditServerModalData
    >(ServerModalComponent, {
      server: this.server as ServerDef
    });
  }

  private handleServerDeletion(): void {
    this.modalService
      .openConfirmationModal({
        message: this.translateService.instant(
          'content.server-deletion-message',
          { serverName: this.server.name }
        )
      })
      .afterClosed()
      .pipe(filter((response) => !!response?.confirms))
      .subscribe(() => this.shellFacade.deleteServer(this.server.id));
  }

  private handleLeaveServer(): void {
    this.modalService
      .openConfirmationModal({
        message: this.translateService.instant(
          'content.you-are-about-to-leave-the-server'
        )
      })
      .afterClosed()
      .pipe(filter((response) => !!response?.confirms))
      .subscribe(() => this.shellFacade.leaveServer(this.server.id));
  }
}
