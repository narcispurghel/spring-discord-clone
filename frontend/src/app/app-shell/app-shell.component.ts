import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Router, RouterModule } from '@angular/router';
import { LoadingSpinnerComponent } from 'src/app/shared/components/loading-spinner/loading-spinner.component';

import { tap } from 'rxjs';
import { ServerModalComponent } from 'src/app/app-shell/components/server-modal/server-modal.component';
import { ModalService } from 'src/app/shared/services/modal.service';
import { LayoutComponent } from './components/layout/layout.component';
import { ServerDef } from './interfaces/server.interface';
import { AppShellFacade } from './store/app-shell.facade';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatDialogModule,
    LoadingSpinnerComponent,

    LayoutComponent
  ],
  providers: [ModalService],
  templateUrl: './app-shell.component.html'
})
export class AppShellComponent implements OnInit {
  constructor(
    private appShellFacade: AppShellFacade,
    private modalService: ModalService,
    private router: Router
  ) {}

  isLoading$ = this.appShellFacade.getIsLoading$('get-servers');
  servers$ = this.appShellFacade.servers$().pipe(
    tap((servers) => {
      this.hasJoinedServers = !!servers.length;
      servers.length
        ? this.closeDialogAndRedirectIfNeeded(servers)
        : this.handleUserWithoutServers();
    })
  );

  private hasJoinedServers = false;
  private dialogRef?: MatDialogRef<ServerModalComponent>;

  ngOnInit(): void {
    this.appShellFacade.getServers();
  }

  private closeDialogAndRedirectIfNeeded(servers: ServerDef[]): void {
    const hasNotBeenRedirected = this.router.url === '/servers';
    if (hasNotBeenRedirected) {
      this.dialogRef?.close();
      this.router.navigate(['servers', servers[0].id]);
    }
  }

  private handleUserWithoutServers(): void {
    this.dialogRef = this.modalService.openRegular(ServerModalComponent);
    this.dialogRef
      .afterClosed()
      .subscribe(
        () => !this.hasJoinedServers && this.handleUserWithoutServers()
      );
  }
}
