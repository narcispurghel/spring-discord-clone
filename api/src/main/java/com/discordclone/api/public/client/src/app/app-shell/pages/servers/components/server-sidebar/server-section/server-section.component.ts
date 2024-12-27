import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { ModalService } from 'src/app/shared/services/modal.service';
import {
  ServerSearchDataType,
  ServerWithMembersAndProfilesDef
} from '../../../interfaces/servers.interfaces';
import { ChannelModalComponent } from '../../channel-modal/channel-modal.component';
import { ManageMembersModalComponent } from '../../manage-members-modal/manage-members-modal.component';
import { ChannelModalData } from '../../../interfaces/channel-modal.interface';

@Component({
  selector: 'app-server-section',
  standalone: true,
  imports: [CommonModule, TranslateModule, MatIconModule, MatTooltipModule],
  templateUrl: './server-section.component.html'
})
export class ServerSectionComponent {
  @Input({ required: true }) sectionType: ServerSearchDataType = 'channel';
  @Input() channelType: ChannelType | undefined;
  @Input() role: MemberRole | undefined;
  @Input({ required: true }) labelKey: string = '';
  @Input() server: ServerWithMembersAndProfilesDef | undefined;

  private modalService = inject(ModalService);

  roles = MemberRole;

  onCreateChannel(): void {
    this.modalService.openRegular<ChannelModalComponent, ChannelModalData>(
      ChannelModalComponent,
      { defaultChannelType: this.channelType }
    );
  }

  onManageMembers(): void {
    this.modalService.openRegular(ManageMembersModalComponent);
  }
}
