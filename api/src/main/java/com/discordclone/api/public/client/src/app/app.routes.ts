import { Routes } from '@angular/router';
import { authGuard } from './authentication/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('./app-shell/app-shell.routes').then((r) => r.SHELL_ROUTES),
    canActivate: [authGuard]
  },
  {
    path: 'authentication',
    loadChildren: () =>
      import('./authentication/auth.routes').then((r) => r.AUTH_ROUES),
    canActivate: [authGuard]
  },
  {
    path: '**',
    redirectTo: '/'
  }
];
