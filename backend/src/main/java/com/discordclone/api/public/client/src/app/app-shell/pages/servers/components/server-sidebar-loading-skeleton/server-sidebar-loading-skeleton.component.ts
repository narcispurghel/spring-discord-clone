import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { SectionTitleSkeletonComponent } from './section-title-skeleton/section-title-skeleton.component';
import { SidebarChannelSkeletonComponent } from './sidebar-channel-skeleton/sidebar-channel-skeleton.component';

@Component({
  selector: 'app-server-sidebar-loading-skeleton',
  standalone: true,
  imports: [
    CommonModule,
    MatDividerModule,
    SectionTitleSkeletonComponent,
    SidebarChannelSkeletonComponent
  ],
  templateUrl: './server-sidebar-loading-skeleton.component.html',
  styleUrl: './server-sidebar-loading-skeleton.component.scss'
})
export class ServerSidebarLoadingSkeletonComponent {}
