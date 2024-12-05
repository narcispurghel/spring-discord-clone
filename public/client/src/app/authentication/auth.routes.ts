import { Routes } from '@angular/router';
import { provideEffects } from '@ngrx/effects';
import { provideState } from '@ngrx/store';
import { storeConstants } from '../shared/constants/store.constant';
import { AuthStoreEffects } from './store/auth.effects';
import { authReducer } from './store/auth.reducer';

export const AUTH_ROUES: Routes = [
  {
    providers: [
      provideState(storeConstants.authState, authReducer),
      provideEffects([AuthStoreEffects])
    ],
    path: '',
    pathMatch: 'full',
    redirectTo: 'login'
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login/login.component').then((c) => c.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./components/register/register.component').then(
        (c) => c.RegisterComponent
      )
  }
];
