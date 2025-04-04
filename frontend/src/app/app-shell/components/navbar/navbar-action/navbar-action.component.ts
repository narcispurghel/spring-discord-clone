import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { ModalService } from 'src/app/shared/services/modal.service';
import { ServerModalComponent } from '../../server-modal/server-modal.component';

@Component({
  selector: 'app-navbar-action',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatTooltipModule, TranslateModule],
  templateUrl: './navbar-action.component.html'
})
export class NavbarActionComponent {
  private modalService = inject(ModalService);

  openCreateServerModal(): void {
    this.modalService.openRegular(ServerModalComponent);
  }
}
