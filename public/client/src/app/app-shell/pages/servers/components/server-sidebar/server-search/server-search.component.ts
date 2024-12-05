import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnDestroy,
  OnInit,
  inject,
  signal
} from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';
import { ModalService } from 'src/app/shared/services/modal.service';
import { OSType, getOS } from 'src/app/shared/utility/navigator.helpers';
import { ServerSearchModalData } from '../../../interfaces/server-search.interfaces';
import { ServerSearchData } from '../../../interfaces/servers.interfaces';
import { SearchModalComponent } from '../../search-modal/search-modal.component';

@Component({
  selector: 'app-server-search',
  standalone: true,
  imports: [CommonModule, MatIconModule, TranslateModule],
  templateUrl: './server-search.component.html'
})
export class ServerSearchComponent implements OnInit, OnDestroy {
  @Input({ required: true }) serverId: string = '';
  @Input({ required: true }) data: ServerSearchData[] = [];

  private modalService = inject(ModalService);

  OS = signal<OSType>('win');

  ngOnInit(): void {
    this.OS.set(getOS());
    document.addEventListener('keydown', this.downEventHandler);
  }

  ngOnDestroy(): void {
    document.removeEventListener('keydown', this.downEventHandler);
  }

  openSearchModal(): void {
    this.modalService.openRegular<SearchModalComponent, ServerSearchModalData>(
      SearchModalComponent,
      { serverSearchData: this.data, serverId: this.serverId }
    );
  }

  private downEventHandler = (e: KeyboardEvent) => {
    if (e.key.toUpperCase() === 'K' && (e.metaKey || e.ctrlKey)) {
      e.preventDefault();
      this.openSearchModal();
    }
  };
}
