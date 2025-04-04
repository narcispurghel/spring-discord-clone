import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { AuthFacade } from 'src/app/authentication/store/auth.facade';
import { UserAvatarComponent } from 'src/app/shared/components/user-avatar/user-avatar.component';
import { ModalService } from 'src/app/shared/services/modal.service';
import { ProfileModalComponent } from '../../../authentication/components/profile-modal/profile-modal.component';
import {
  USER_MENU_OPTIONS,
  UserMenuItemType
} from '../../constants/user-menu.constants';

@Component({
  selector: 'app-user-button',
  standalone: true,
  imports: [
    CommonModule,
    MatMenuModule,
    TranslateModule,
    MatIconModule,
    MatTooltipModule,

    UserAvatarComponent
  ],
  templateUrl: './user-button.component.html'
})
export class UserButtonComponent {
  private authFacade = inject(AuthFacade);
  private modalService = inject(ModalService);

  user$ = this.authFacade.getUser$();
  userMenuOptions = USER_MENU_OPTIONS;

  onOptionClicked(optionType: UserMenuItemType): void {
    switch (optionType) {
      case 'update-profile':
        this.modalService.openRegular(ProfileModalComponent);
        break;
      case 'logout':
        this.authFacade.logout();
        break;
    }
  }
}
