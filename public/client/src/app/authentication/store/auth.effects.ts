import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Actions, createEffect, ofType } from '@ngrx/effects';

import { catchError, map, of, switchMap, tap } from 'rxjs';

import { Router } from '@angular/router';
import { SnackBarService } from 'src/app/shared/services/snackbar.service';
import { AuthService } from '../services/auth.service';
import * as actions from './auth.actions';

@Injectable({ providedIn: 'root' })
export class AuthStoreEffects {
  constructor(
    private actions$: Actions,
    private authService: AuthService,
    private snackbarService: SnackBarService,
    private router: Router
  ) {}

  registerRequest$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.RegisterRequestAction),
      switchMap((action) =>
        this.authService.register$(action.registerFields).pipe(
          map((user) => actions.RegisterSuccessAction({ user })),
          catchError((error) => {
            this.snackbarService.openSnackBar(
              error.status === 400
                ? 'validators.this-user-already-exists'
                : 'content.oops-something-went-wrong',
              'warning'
            );
            return of(actions.RegisterFailureAction());
          })
        )
      )
    )
  );

  loginRequest$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.LoginRequestAction),
      switchMap((action) =>
        this.authService.login$(action.loginFields).pipe(
          map((user) => actions.LoginSuccessAction({ user })),
          catchError((error: HttpErrorResponse) => {
            this.snackbarService.openSnackBar(
              error.status === 401
                ? 'validators.wrong-email-or-password'
                : 'content.oops-something-went-wrong',
              'warning'
            );
            return of(actions.LoginFailureAction());
          })
        )
      )
    )
  );

  loginSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.LoginSuccessAction),
        tap(() => {
          this.authService.handleRoutingOnAuthCompleted();
        })
      ),
    { dispatch: false }
  );

  registerSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.RegisterSuccessAction),
        tap(() => {
          this.authService.handleRoutingOnAuthCompleted();
          this.snackbarService.openSnackBar(
            'content.you-have-been-successfully-registered',
            'success'
          );
        })
      ),
    { dispatch: false }
  );

  logoutRequest$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.LogoutRequestAction),
        switchMap(() => this.authService.logout$())
      ),
    { dispatch: false }
  );

  logoutSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.LogoutSuccessAction),
        tap((action) => {
          this.snackbarService.openSnackBar(
            action.messageKey
              ? action.messageKey
              : 'content.you-have-been-logged-out'
          );

          this.router.navigate(['authentication']);
        })
      ),
    { dispatch: false }
  );

  updateProfileRequest$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.UpdateProfileRequestAction),
      switchMap((action) =>
        this.authService.updateProfile$(action.data).pipe(
          map((user) => actions.UpdateProfileSuccessAction({ user })),
          catchError((error: HttpErrorResponse) =>
            of(actions.UpdateProfileFailureAction())
          )
        )
      )
    )
  );

  updateProfileSuccess$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.UpdateProfileSuccessAction),
        tap(({ user }) => {
          this.snackbarService.openSnackBar(
            'content.your-profile-has-been-updated',
            'success'
          );
        })
      ),
    { dispatch: false }
  );

  updateProfileFailure$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.UpdateProfileFailureAction),
        tap(() => {
          this.snackbarService.openSnackBar(
            'content.something-went-wrong',
            'warning'
          );
        })
      ),
    { dispatch: false }
  );
}
