import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-section-title-skeleton',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './section-title-skeleton.component.html',
  styleUrl: '../server-sidebar-loading-skeleton.component.scss'
})
export class SectionTitleSkeletonComponent {}
