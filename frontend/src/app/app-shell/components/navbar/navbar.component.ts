import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { AppShellFacade } from '../../store/app-shell.facade';
import { UserButtonComponent } from '../user-button/user-button.component';
import { NavbarActionComponent } from './navbar-action/navbar-action.component';
import { NavbarItemComponent } from './navbar-item/navbar-item.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    MatDividerModule,
    NavbarActionComponent,
    NavbarItemComponent,
    UserButtonComponent
  ],
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  private shellFacade = inject(AppShellFacade);

  activeStoreId$ = this.shellFacade.getActiveServerId$();
  servers$ = this.shellFacade.servers$();
}
