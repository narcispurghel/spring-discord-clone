import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { debounceTime } from 'rxjs';
import { SidenavService } from 'src/app/app-shell/services/sidenav.service';
import { UserAvatarComponent } from 'src/app/shared/components/user-avatar/user-avatar.component';
import { ROLE_ICON_MAP } from 'src/app/shared/constants/role-icon-map.constant';
import {
  MemberWithProfileDef,
  ServerWithChannelsMembersAndProfilesDef
} from '../../../interfaces/servers.interfaces';
import { ServersFacade } from '../../../store/servers.facade';

@Component({
  selector: 'app-server-member',
  standalone: true,
  imports: [CommonModule, UserAvatarComponent, MatIconModule],
  templateUrl: './server-member.component.html'
})
export class ServerMemberComponent {
  @Input({ required: true }) member!: MemberWithProfileDef;
  @Input({ required: true }) server!: ServerWithChannelsMembersAndProfilesDef;

  private router = inject(Router);
  private sidenavService = inject(SidenavService);

  conversationId$ = inject(ServersFacade)
    .getActiveMemberConversationId$()
    .pipe(debounceTime(0));
  rolesIconsMap = ROLE_ICON_MAP;

  onClick(): void {
    this.router.navigate([
      'servers',
      this.server.id,
      'conversations',
      this.member.id
    ]);

    this.sidenavService.closeIfOpen();
  }
}
