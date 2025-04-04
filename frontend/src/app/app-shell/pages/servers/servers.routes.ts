import { Routes } from '@angular/router';
import { provideEffects } from '@ngrx/effects';
import { provideState } from '@ngrx/store';
import { storeConstants } from 'src/app/shared/constants/store.constant';
import { ChannelsStoreEffects } from './pages/channels/store/channels.effects';
import { channelsReducer } from './pages/channels/store/channels.reducer';
import { ServersComponent } from './servers.component';
import { ChatService } from './services/chat.service';
import { ServersService } from './services/servers.service';
import { ServersStoreEffects } from './store/servers.effects';
import { ServersFacade } from './store/servers.facade';
import { serversReducer } from './store/servers.reducer';

export const SERVERS_ROUTES: Routes = [
  {
    path: ':serverId',
    component: ServersComponent,
    providers: [
      provideState(storeConstants.serversState, serversReducer),
      provideEffects([ServersStoreEffects]),
      ServersFacade,
      ServersService,
      ChatService
    ],
    children: [
      {
        path: 'channels',
        loadChildren: () =>
          import('./pages/channels/channels.routes').then(
            (r) => r.CHANNELS_ROUTES
          ),
        providers: [
          provideState(storeConstants.channelsState, channelsReducer),
          provideEffects([ChannelsStoreEffects])
        ]
      },
      {
        path: 'conversations',
        loadChildren: () =>
          import('./pages/conversations/conversations.routes').then(
            (r) => r.CONVERSATIONS_ROUTES
          )
      }
    ]
  }
];
