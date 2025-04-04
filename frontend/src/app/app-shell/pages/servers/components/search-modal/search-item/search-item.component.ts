import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import {
  ServerSearchData,
  ServerSearchDataType
} from '../../../interfaces/servers.interfaces';

@Component({
  selector: 'app-search-item',
  standalone: true,
  imports: [CommonModule, TranslateModule, MatIconModule],
  templateUrl: './search-item.component.html'
})
export class SearchItemComponent {
  @Input({ required: true }) serverId: string = '';
  @Input({ required: true }) data!: ServerSearchData;

  private router = inject(Router);

  onClick(id: string, type: ServerSearchDataType): void {
    type === 'member'
      ? this.router.navigate(['servers', this.serverId, 'conversations', id])
      : this.router.navigate(['servers', this.serverId, 'channels', id]);
  }
}
