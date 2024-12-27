import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { catchError, map, of, switchMap, tap, withLatestFrom } from 'rxjs';
import { SnackBarService } from 'src/app/shared/services/snackbar.service';
import { AppState } from 'src/app/store/app.state';
import { GetServerWithChannelsMembersAndProfilesAction } from '../pages/servers/store/servers.actions';
import { AppShellService } from '../services/app-shell.service';
import * as actions from './app-shell.actions';
import * as selectors from './app-shell.selectors';

@Injectable({ providedIn: 'root' })
export class AppShellStoreEffects {
  constructor(
    private actions$: Actions,
    private shellService: AppShellService,
    private snackbarService: SnackBarService,
    private router: Router,
    private store: Store<AppState>
  ) {}

  createServerAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.CreateServerAction),
      switchMap((action) =>
        this.shellService.createServer$(action.data).pipe(
          map((server) => actions.CreateServerSuccessAction({ server })),
          catchError((error) => of(actions.CreateServerFailureAction()))
        )
      )
    )
  );

  createServerSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.CreateServerSuccessAction),
        tap(() =>
          this.snackbarService.openSnackBar(
            'content.server-created-successfully',
            'success'
          )
        ),
        tap(({ server }) => this.router.navigate(['servers', server.id]))
      ),
    { dispatch: false }
  );

  createServerFailureAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.CreateServerFailureAction),
        tap(() =>
          this.snackbarService.openSnackBar(
            'content.creating-the-server-has-failed',
            'warning'
          )
        )
      ),
    { dispatch: false }
  );

  editServerAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.EditServerAction),
      switchMap(({ serverId, data }) =>
        this.shellService.editServer$({ serverId, data }).pipe(
          map((server) => actions.EditServerSuccessAction({ server })),
          catchError(() => of(actions.EditServerFailureAction()))
        )
      )
    )
  );

  editServerSuccessAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.EditServerSuccessAction),
      tap(() =>
        this.snackbarService.openSnackBar(
          'content.server-updated-successfully',
          'success'
        )
      ),
      switchMap(({ server }) =>
        of(
          GetServerWithChannelsMembersAndProfilesAction({
            serverId: server.id
          })
        )
      )
    )
  );

  deleteServerAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.DeleteServerAction),
      switchMap(({ serverId }) =>
        this.shellService.deleteServer$(serverId).pipe(
          map(() =>
            actions.DeleteServerSuccessAction({ deletedServerId: serverId })
          ),
          catchError(() => of(actions.DeleteServerFailureAction()))
        )
      )
    )
  );

  deleteServerSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.DeleteServerSuccessAction),
        tap(() =>
          this.snackbarService.openSnackBar(
            'content.the-server-has-been-deleted-successfully',
            'success'
          )
        ),
        withLatestFrom(this.store.select(selectors.activeServerId)),
        tap(([_, activeStoreId]) =>
          this.router.navigate(
            activeStoreId ? ['servers', activeStoreId] : ['servers']
          )
        )
      ),
    { dispatch: false }
  );

  leaveServerAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.LeaveServerAction),
      switchMap(({ serverId }) =>
        this.shellService.leaveServer$(serverId).pipe(
          map(() =>
            actions.LeaveServerSuccessAction({ leftServerId: serverId })
          ),
          catchError(() => of(actions.LeaveServerFailureAction()))
        )
      )
    )
  );

  leaveServerSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.LeaveServerSuccessAction),
        tap(() =>
          this.snackbarService.openSnackBar(
            'content.you-have-left-the-server',
            'success'
          )
        ),
        withLatestFrom(this.store.select(selectors.activeServerId)),
        tap(([_, activeStoreId]) =>
          this.router.navigate(
            activeStoreId ? ['servers', activeStoreId] : ['servers']
          )
        )
      ),
    { dispatch: false }
  );

  joinServerAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.JoinServerAction),
      switchMap(({ inviteCode }) =>
        this.shellService.joinServer$(inviteCode).pipe(
          map(() => actions.JoinServerSuccessAction()),
          catchError((error) => of(actions.JoinServerFailureAction({ error })))
        )
      )
    )
  );

  joinServerSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.JoinServerSuccessAction),
        tap(() => this.router.navigate(['/']))
      ),
    { dispatch: false }
  );

  joinServerFailureAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.JoinServerFailureAction),
        tap((error) => {
          if (
            error?.error?.error?.message === 'Invalid or expired invite code!'
          ) {
            this.router.navigate(['/']);
            this.snackbarService.openSnackBar(
              'content.this-invite-code-has-expired-or-is-invalid',
              'warning'
            );
          }
        })
      ),
    { dispatch: false }
  );

  getServersAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.GetServersAction),
      switchMap(() =>
        this.shellService.getServers$().pipe(
          map((servers) => actions.GetServersSuccessAction({ servers })),
          catchError(() => of(actions.GetServersFailureAction()))
        )
      )
    )
  );
}
