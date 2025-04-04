import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Socket } from 'ngx-socket-io';
import { takeWhile } from 'rxjs';
import { LoadingSpinnerComponent } from 'src/app/shared/components/loading-spinner/loading-spinner.component';
import { AppShellFacade } from '../../store/app-shell.facade';
import { ServerSidebarLoadingSkeletonComponent } from './components/server-sidebar-loading-skeleton/server-sidebar-loading-skeleton.component';
import { ServerSidebarComponent } from './components/server-sidebar/server-sidebar.component';
import { SocketStatus } from './services/socket-status.service';
import { ServersFacade } from './store/servers.facade';

@Component({
  selector: 'app-servers',
  standalone: true,
  imports: [
    CommonModule,
    ServerSidebarComponent,
    ServerSidebarLoadingSkeletonComponent,
    RouterModule,
    LoadingSpinnerComponent
  ],
  templateUrl: './servers.component.html'
})
export class ServersComponent implements OnInit, OnDestroy {
  private route = inject(ActivatedRoute);
  private shellFacade = inject(AppShellFacade);
  private serversFacade = inject(ServersFacade);
  private socketService = inject(Socket);
  private socketStatus = inject(SocketStatus);

  isLoading$ = this.serversFacade.getIsLoading$(
    'get-server-with-channels-members-and-profiles'
  );

  serverAndUser$ = this.serversFacade.serverAndUser$();

  private alive = true;

  ngOnInit(): void {
    this.subscribeServerId();
    this.socketService.connect();
    this.socketService.on('connect', () =>
      this.socketStatus.isConnected.set(true)
    );
    this.socketService.on('disconnect', () =>
      this.socketStatus.isConnected.set(false)
    );
  }

  ngOnDestroy(): void {
    this.alive = false;
    this.socketService.disconnect();
    this.socketService.off('connect');
    this.socketService.off('disconnect');
  }

  private subscribeServerId(): void {
    this.route.paramMap
      .pipe(takeWhile(() => this.alive))
      .subscribe((params) => {
        const serverId = params.get('serverId') || '';
        this.serversFacade.getServerWithChannelsMembersAndProfiles(serverId);
        this.shellFacade.setActiveServerId(serverId);
      });
  }
}
