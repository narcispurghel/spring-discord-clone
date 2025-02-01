import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, map } from 'rxjs';

import { AppState } from 'src/app/store/app.state';
import {
  LoginFields,
  RegisterFields,
  UpdateProfileDto,
  UserDef
} from '../interfaces/auth.interface';
import * as actions from './auth.actions';
import { authState, authUser } from './auth.selectors';
import { AuthIsLoadingKey, AuthState } from './auth.state';

@Injectable({ providedIn: 'root' })
export class AuthFacade {
  constructor(private store: Store<AppState>) {}

  getAuthState$(): Observable<AuthState> {
    return this.store.select(authState);
  }

  getUser$(): Observable<UserDef | null> {
    return this.store.select(authUser);
  }

  isLoggedIn$(): Observable<boolean> {
    return this.store.select(authUser).pipe(map((user) => !!user));
  }

  getIsLoading$(loadingKey: AuthIsLoadingKey): Observable<boolean> {
    return this.store
      .select((state: AppState) => state.authState.isLoading)
      .pipe(
        map((loadingKeys) => loadingKeys.some((key) => key === loadingKey))
      );
  }

  register(registerFields: RegisterFields): void {
    this.store.dispatch(actions.RegisterRequestAction({ registerFields }));
  }

  login(loginFields: LoginFields): void {
    this.store.dispatch(actions.LoginRequestAction({ loginFields }));
  }

  logout(): void {
    this.store.dispatch(actions.LogoutRequestAction());
  }

  updateProfile(data: UpdateProfileDto): void {
    this.store.dispatch(actions.UpdateProfileRequestAction({ data }));
  }
}
