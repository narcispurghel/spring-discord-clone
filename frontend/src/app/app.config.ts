import {
  ApplicationConfig,
  importProvidersFrom,
  isDevMode
} from '@angular/core';
import {
  PreloadAllModules,
  provideRouter,
  withComponentInputBinding,
  withPreloading
} from '@angular/router';
import { provideEffects } from '@ngrx/effects';
import { provideStore } from '@ngrx/store';
import { provideStoreDevtools } from '@ngrx/store-devtools';

import {
  HttpBackend,
  provideHttpClient,
  withInterceptorsFromDi
} from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';

import { MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { provideAnimations } from '@angular/platform-browser/animations';

import { SocketIoConfig, SocketIoModule } from 'ngx-socket-io';
import { environment } from 'src/environments/environment';
import { AppShellStoreEffects } from './app-shell/store/app-shell.effects';
import { routes } from './app.routes';
import { AuthStoreEffects } from './authentication/store/auth.effects';
import { CustomTranslateLoader } from './shared/factories/translate-loader.factory';
import { GLOBAL_INTERCEPTORS } from './shared/interceptors';
import { metaReducers } from './store/app-store.reducers';
import { appReducers } from './store/app.reducer';

const socketConfig: SocketIoConfig = {
  url: environment.socketUrl,
  options: { withCredentials: true }
};

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(
      routes,
      withPreloading(PreloadAllModules),
      withComponentInputBinding()
    ),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()),
    provideStore(appReducers, { metaReducers }),
    provideEffects([AuthStoreEffects, AppShellStoreEffects]),
    provideStoreDevtools({
      maxAge: 25, // Retains last 25 states
      logOnly: !isDevMode(), // Restrict extension to log-only mode
      autoPause: true, // Pauses recording actions and state changes when the extension window is not open
      trace: false, //  If set to true, will include stack trace for every dispatched action, so you can see it in trace tab jumping directly to that part of code
      traceLimit: 75 // maximum stack trace frames to be stored (in case trace option was provided as true)
    }),
    importProvidersFrom(
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useClass: CustomTranslateLoader,
          deps: [HttpBackend]
        }
      }),
      MatSnackBarModule,
      SocketIoModule.forRoot(socketConfig)
    ),
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'outline' }
    },
    ...GLOBAL_INTERCEPTORS
  ]
};
