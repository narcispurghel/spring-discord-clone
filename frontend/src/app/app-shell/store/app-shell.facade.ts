import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import { AppState } from 'src/app/store/app.state';

import { Observable, map } from 'rxjs';
import { CreateEditServerDto } from '../interfaces/create-edit-server.interface';
import { ServerDef } from '../interfaces/server.interface';
import * as actions from './app-shell.actions';

import * as selectors from './app-shell.selectors';
import { AppShellIsLoadingKey } from './app-shell.state';

@Injectable({ providedIn: 'root' })
export class AppShellFacade {
  constructor(private store: Store<AppState>) {}

  createServer(data: CreateEditServerDto): void {
    this.store.dispatch(actions.CreateServerAction({ data }));
  }

  deleteServer(serverId: string): void {
    this.store.dispatch(actions.DeleteServerAction({ serverId }));
  }

  leaveServer(serverId: string): void {
    this.store.dispatch(actions.LeaveServerAction({ serverId }));
  }

  editServer(serverId: string, data: CreateEditServerDto): void {
    this.store.dispatch(actions.EditServerAction({ serverId, data }));
  }

  servers$(): Observable<ServerDef[]> {
    return this.store.select(selectors.appShellServers);
  }

  getServers(): void {
    this.store.dispatch(actions.GetServersAction());
  }

  getIsLoading$(loadingKey: AppShellIsLoadingKey): Observable<boolean> {
    return this.store
      .select((state: AppState) => state.appShellState.isLoading)
      .pipe(
        map((loadingKeys) => loadingKeys.some((key) => key === loadingKey))
      );
  }

  joinServer(inviteCode: string): void {
    this.store.dispatch(actions.JoinServerAction({ inviteCode }));
  }

  setActiveServerId(serverId: string): void {
    this.store.dispatch(actions.SetActiveServerId({ serverId }));
  }

  getActiveServerId$(): Observable<string> {
    return this.store.select(selectors.activeServerId);
  }
}
