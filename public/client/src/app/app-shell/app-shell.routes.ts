import { Routes } from '@angular/router';
import { provideEffects } from '@ngrx/effects';
import { provideState } from '@ngrx/store';
import { storeConstants } from 'src/app/shared/constants/store.constant';
import { AppShellComponent } from './app-shell.component';
import { InviteComponent } from './pages/invite/invite.component';
import { AppShellStoreEffects } from './store/app-shell.effects';
import { appShellReducer } from './store/app-shell.reducer';

export const SHELL_ROUTES: Routes = [
  {
    path: '',
    redirectTo: 'servers',
    pathMatch: 'full'
  },
  {
    path: '',
    component: AppShellComponent,
    providers: [
      provideState(storeConstants.appShellState, appShellReducer),
      provideEffects([AppShellStoreEffects])
    ],
    children: [
      {
        path: 'servers',
        loadChildren: () =>
          import('./pages/servers/servers.routes').then((r) => r.SERVERS_ROUTES)
      }
    ]
  },
  {
    path: 'invite',
    component: InviteComponent
  }
];
