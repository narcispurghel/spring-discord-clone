import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';
import { debounceTime } from 'rxjs';
import { SidenavService } from 'src/app/app-shell/services/sidenav.service';
import { ServerSearchModalData } from '../../interfaces/server-search.interfaces';
import { ServerSearchData } from '../../interfaces/servers.interfaces';
import { SearchItemComponent } from './search-item/search-item.component';

@Component({
  selector: 'app-search-modal',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatDividerModule,
    TranslateModule,
    ReactiveFormsModule,
    SearchItemComponent
  ],
  templateUrl: './search-modal.component.html'
})
export class SearchModalComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: ServerSearchModalData,
    private dialogRef: MatDialogRef<SearchModalComponent>,
    private sidenavService: SidenavService
  ) {
    this.filteredItems.set(data.serverSearchData);
    this.serverId = data.serverId;
  }

  serverId = '';
  control = new FormControl('');
  filteredItems = signal<ServerSearchData[]>([]);

  ngOnInit(): void {
    this.handleFiltering();
  }

  closeDialogAndSidenav(): void {
    this.closeDialog();
    this.sidenavService.closeIfOpen();
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  private handleFiltering(): void {
    this.control.valueChanges
      .pipe(debounceTime(100))
      .subscribe((inputValue) => {
        this.filteredItems.set(
          this.data.serverSearchData
            .map((searchItem) => ({
              ...searchItem,
              data: (searchItem.data || []).filter(
                (item) =>
                  item.name
                    .toLowerCase()
                    .indexOf((inputValue || '').toLowerCase()) > -1
              )
            }))
            .filter((searchItem) => !!searchItem.data?.length)
        );
      });
  }
}
