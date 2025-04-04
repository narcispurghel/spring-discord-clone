import { CommonModule } from '@angular/common';
import { Component, Input, booleanAttribute } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sidebar-channel-skeleton',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './sidebar-channel-skeleton.component.html',
  styleUrl: '../server-sidebar-loading-skeleton.component.scss'
})
export class SidebarChannelSkeletonComponent {
  @Input({ transform: booleanAttribute }) withLockIcon = false;
}
