import { createFeatureSelector, createSelector } from '@ngrx/store';
import { storeConstants } from 'src/app/shared/constants/store.constant';
import { ServersState } from './servers.state';

export const serversState = createFeatureSelector<ServersState>(
  storeConstants.serversState
);

export const serversCurrentServer = createSelector(
  serversState,
  (state: ServersState) => state.currentServer
);

export const activeServerId = createSelector(
  serversState,
  (state: ServersState) => state.activeServerId
);

export const activeChannelId = createSelector(
  serversState,
  (state: ServersState) => state.activeChannelId
);

export const activeMemberConversationId = createSelector(
  serversState,
  (state: ServersState) => state.activeMemberConversationId
);
