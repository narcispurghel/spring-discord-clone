import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule, MatMenuTrigger } from '@angular/material/menu';
import { TranslateModule } from '@ngx-translate/core';
import { tap } from 'rxjs';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { LoadingSpinnerComponent } from 'src/app/shared/components/loading-spinner/loading-spinner.component';
import { ModalBodyComponent } from 'src/app/shared/components/modal/modal-body/modal-body.component';
import { ModalSubtitleComponent } from 'src/app/shared/components/modal/modal-subtitle/modal-subtitle.component';
import { ModalTitleComponent } from 'src/app/shared/components/modal/modal-title/modal-title.component';
import { ModalComponent } from 'src/app/shared/components/modal/modal.component';
import { UserAvatarComponent } from 'src/app/shared/components/user-avatar/user-avatar.component';
import { ROLE_ICON_MAP } from 'src/app/shared/constants/role-icon-map.constant';
import { ServersFacade } from '../../store/servers.facade';

@Component({
  selector: 'app-manage-members-modal',
  standalone: true,
  imports: [
    CommonModule,
    ModalComponent,
    ModalTitleComponent,
    ModalSubtitleComponent,
    ModalBodyComponent,
    TranslateModule,
    UserAvatarComponent,
    LoadingSpinnerComponent,
    MatIconModule,
    MatMenuModule,
    MatDividerModule
  ],
  templateUrl: './manage-members-modal.component.html'
})
export class ManageMembersModalComponent {
  constructor(private serversFacade: ServersFacade) {}

  isLoading$ = this.serversFacade
    .getIsLoading$('change-member-role')
    .pipe(tap((isLoading) => !isLoading && this.loadingId.set('')));
  server$ = this.serversFacade.currentServerData$();
  roleIconMap = ROLE_ICON_MAP;
  memberRoles = MemberRole;
  loadingId = signal('');

  toggleRoleMenu(event: MouseEvent, trigger: MatMenuTrigger): void {
    event.stopPropagation();
    trigger.menuOpen ? trigger.openMenu() : trigger.closeMenu();
  }

  onKick(memberId: string): void {
    this.loadingId.set(memberId);
    this.serversFacade.kickMember(memberId);
  }

  onChangeRole(memberId: string, role: MemberRole): void {
    this.loadingId.set(memberId);
    this.serversFacade.changeMemberRole({ memberId, role });
  }
}
