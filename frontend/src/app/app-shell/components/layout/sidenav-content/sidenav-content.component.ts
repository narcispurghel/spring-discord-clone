import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  OnDestroy,
  OnInit,
  Output,
  inject
} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ServerSidebarComponent } from 'src/app/app-shell/pages/servers/components/server-sidebar/server-sidebar.component';
import { ServersFacade } from 'src/app/app-shell/pages/servers/store/servers.facade';
import { SidenavService } from 'src/app/app-shell/services/sidenav.service';
import { AuthFacade } from 'src/app/authentication/store/auth.facade';
import { NavbarComponent } from '../../navbar/navbar.component';
import { takeWhile } from 'rxjs';

@Component({
  selector: 'app-sidenav-content',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    ServerSidebarComponent,
    NavbarComponent,
    TranslateModule,
    RouterModule,
    MatIconModule
  ],
  templateUrl: './sidenav-content.component.html'
})
export class SidenavContentComponent implements OnInit, OnDestroy {
  private authFacade = inject(AuthFacade);
  private serversFacade = inject(ServersFacade);

  @Output() onItemClicked = new EventEmitter();

  isLoggingOut$ = this.authFacade.getIsLoading$('logout');
  sidenavOpen$ = inject(SidenavService).isOpen$;
  serverAndUser$ = this.serversFacade.serverAndUser$();

  private alive = true;

  ngOnInit(): void {
    this.handleMenuOnLogout();
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  onLogout(): void {
    this.onItemClicked.emit();
    this.authFacade.logout();
  }

  private handleMenuOnLogout(): void {
    this.authFacade
      .getIsLoading$('logout')
      .pipe(takeWhile(() => this.alive))
      .subscribe((isLoggingOut) => {
        const isOpen = this.sidenavOpen$.value;
        isLoggingOut && isOpen && this.sidenavOpen$.next(!isOpen);
      });
  }
}
