import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import { AppState } from 'src/app/store/app.state';

import { Observable, combineLatest, filter, map } from 'rxjs';
import { ChannelDef } from 'src/app/app-shell/interfaces/channel.interface';
import { UserDef } from 'src/app/authentication/interfaces/auth.interface';
import { AuthFacade } from 'src/app/authentication/store/auth.facade';
import {
  ChangeMemberRolePayload,
  CreateChannelPayload,
  EditChannelPayload
} from '../interfaces/manage-members-modal.interface';
import {
  MemberWithProfileDef,
  ServerWithChannelsMembersAndProfilesDef
} from '../interfaces/servers.interfaces';
import * as actions from './servers.actions';
import * as selectors from './servers.selectors';
import { ServersIsLoadingKey } from './servers.state';

@Injectable({ providedIn: 'root' })
export class ServersFacade {
  constructor(
    private store: Store<AppState>,
    private authFacade: AuthFacade
  ) {}

  createChannel(data: Omit<CreateChannelPayload, 'serverId'>): void {
    this.store.dispatch(actions.CreateChannelAction(data));
  }

  editChannel(data: Omit<EditChannelPayload, 'serverId'>): void {
    this.store.dispatch(actions.EditChannelAction(data));
  }

  deleteChannel(channelId: string): void {
    this.store.dispatch(actions.DeleteChannelAction({ channelId }));
  }

  changeMemberRole(data: Omit<ChangeMemberRolePayload, 'serverId'>): void {
    this.store.dispatch(actions.ChangeMemberRoleAction(data));
  }

  generateNewInviteCode(serverId: string): void {
    this.store.dispatch(actions.GenerateNewInviteCodeAction({ serverId }));
  }

  kickMember(memberId: string): void {
    this.store.dispatch(actions.KickMemberAction({ memberId }));
  }

  getServerWithChannelsMembersAndProfiles(serverId: string): void {
    this.store.dispatch(
      actions.GetServerWithChannelsMembersAndProfilesAction({ serverId })
    );
  }

  getChannelFromCurrentServer$(
    channelId: string
  ): Observable<ChannelDef | undefined> {
    return this.store
      .select(selectors.serversCurrentServer)
      .pipe(
        map((server) =>
          server.channels.find((channel) => channel.id === channelId)
        )
      );
  }

  getMemberWithProfileFromCurrentServer$(
    memberId: string
  ): Observable<MemberWithProfileDef | undefined> {
    return this.store
      .select(selectors.serversCurrentServer)
      .pipe(
        map((server) =>
          server.members.find((member) => member.id === memberId)
        )
      );
  }

  serverAndUser$() {
    return combineLatest([
      this.authFacade.getUser$(),
      this.currentServerData$()
    ]).pipe(
      filter(([user, server]) => !!user && !!server),
      map(([user, server]) => ({
        user,
        server
      }))
    ) as Observable<{
      user: UserDef;
      server: ServerWithChannelsMembersAndProfilesDef;
    }>;
  }

  currentServerData$(): Observable<ServerWithChannelsMembersAndProfilesDef | null> {
    return this.store.select(selectors.serversCurrentServer);
  }

  setActiveChannelId(channelId: string): void {
    this.store.dispatch(actions.SetActiveChannelId({ channelId }));
  }

  setActiveMemberConversationId(conversationId: string): void {
    this.store.dispatch(actions.SetActiveConversationId({ conversationId }));
  }

  getActiveChannelId$(): Observable<string> {
    return this.store.select(selectors.activeChannelId);
  }

  getActiveMemberConversationId$(): Observable<string> {
    return this.store.select(selectors.activeMemberConversationId);
  }

  getIsLoading$(loadingKey: ServersIsLoadingKey): Observable<boolean> {
    return this.store
      .select((state: AppState) => state.serversState.isLoading)
      .pipe(
        map((loadingKeys) => loadingKeys.some((key) => key === loadingKey))
      );
  }
}
