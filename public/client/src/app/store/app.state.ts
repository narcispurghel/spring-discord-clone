import { ChannelsState } from '../app-shell/pages/servers/pages/channels/store/channels.state';
import { ServersState } from '../app-shell/pages/servers/store/servers.state';
import { AppShellState } from '../app-shell/store/app-shell.state';
import { AuthState } from '../authentication/store/auth.state';
import { storeConstants } from '../shared/constants/store.constant';

export interface AppState {
  [storeConstants.authState]: AuthState;
  [storeConstants.appShellState]: AppShellState;
  [storeConstants.serversState]: ServersState;
  [storeConstants.channelsState]: ChannelsState;
}
