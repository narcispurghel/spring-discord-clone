import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { UserDef } from 'src/app/authentication/interfaces/auth.interface';

type Size = 'small' | 'medium' | 'large';

@Component({
  selector: 'app-user-avatar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-avatar.component.html',
  host: {
    class: 'block'
  }
})
export class UserAvatarComponent {
  @Input() size: Size = 'medium';
  @Input({ required: true }) user!: UserDef;

  getUserInitials(username: string): string {
    if (!username) {
      return ""
    }
    const splittedUsername = username.split(' ');
    const first = splittedUsername[0];
    const last = splittedUsername.at(-1);
    return `${first[0].toUpperCase()}${
      last && last !== first ? last[0].toUpperCase() : ''
    }`;
  }
}
