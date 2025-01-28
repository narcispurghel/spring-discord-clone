import { createFeatureSelector, createSelector } from '@ngrx/store';
import { storeConstants } from 'src/app/shared/constants/store.constant';
import { AppShellState } from './app-shell.state';

export const appShellState = createFeatureSelector<AppShellState>(
  storeConstants.appShellState
);

export const appShellServers = createSelector(
  appShellState,
  (state: AppShellState) => state.servers
);

export const activeServerId = createSelector(
  appShellState,
  (state: AppShellState) => state.activeServerId
);
