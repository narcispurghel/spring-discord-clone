import { ActionReducerMap } from '@ngrx/store';

import { channelsReducer } from '../app-shell/pages/servers/pages/channels/store/channels.reducer';
import { serversReducer } from '../app-shell/pages/servers/store/servers.reducer';
import { appShellReducer } from '../app-shell/store/app-shell.reducer';
import { authReducer } from '../authentication/store/auth.reducer';
import { storeConstants } from '../shared/constants/store.constant';
import { AppState } from './app.state';

export const appReducers: ActionReducerMap<AppState> = {
  [storeConstants.authState]: authReducer,
  [storeConstants.appShellState]: appShellReducer,
  [storeConstants.serversState]: serversReducer,
  [storeConstants.channelsState]: channelsReducer
};
