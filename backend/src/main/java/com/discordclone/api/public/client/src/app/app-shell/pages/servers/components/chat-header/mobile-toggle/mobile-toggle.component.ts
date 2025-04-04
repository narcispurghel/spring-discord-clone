import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { SidenavService } from 'src/app/app-shell/services/sidenav.service';

@Component({
  selector: 'app-mobile-toggle',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './mobile-toggle.component.html'
})
export class MobileToggleComponent {
  private sidenavService = inject(SidenavService);

  toggleSidenav(): void {
    this.sidenavService.toggleSidenav();
  }
}
