import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { catchError, map, of, switchMap, tap, withLatestFrom } from 'rxjs';
import { activeServerId } from 'src/app/app-shell/store/app-shell.selectors';
import { SnackBarService } from 'src/app/shared/services/snackbar.service';
import { AppState } from 'src/app/store/app.state';
import { ServersService } from '../services/servers.service';
import * as actions from './servers.actions';
import { activeChannelId, serversCurrentServer } from './servers.selectors';

@Injectable({ providedIn: 'root' })
export class ServersStoreEffects {
  constructor(
    private actions$: Actions,
    private serversService: ServersService,
    private store$: Store<AppState>,
    private snackbarService: SnackBarService,
    private dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  getServerWithChannelsMembersAndProfilesAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.GetServerWithChannelsMembersAndProfilesAction),
      switchMap(({ serverId }) =>
        this.serversService
          .getServerWithChannelsMembersAndProfiles$(serverId)
          .pipe(
            map((server) =>
              actions.GetServerWithChannelsMembersAndProfilesSuccessAction({
                server
              })
            ),
            catchError((error) =>
              of(actions.GetServerWithChannelsMembersAndProfilesFailureAction())
            )
          )
      )
    )
  );

  getServerWithChannelsMembersAndProfilesSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.GetServerWithChannelsMembersAndProfilesSuccessAction),
        withLatestFrom(this.store$.select(activeServerId)),
        tap(
          ([{ server }, activeServerId]) =>
            this.router.url.split('/').length === 3 &&
            this.router.navigate([
              'servers',
              activeServerId,
              'channels',
              server.channels[0].id
            ])
        )
      ),
    { dispatch: false }
  );

  generateNewInviteCodeAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.GenerateNewInviteCodeAction),
      switchMap(({ serverId }) =>
        this.serversService.generateNewInviteCode$(serverId).pipe(
          map((server) =>
            actions.GenerateNewInviteCodeSuccessAction({
              server
            })
          ),
          catchError((error) =>
            of(actions.GenerateNewInviteCodeFailureAction())
          )
        )
      )
    )
  );

  changeMemberRoleAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.ChangeMemberRoleAction),
      withLatestFrom(this.store$.select(serversCurrentServer)),
      switchMap(([{ memberId, role }, currentServer]) =>
        this.serversService
          .changeMemberRole$({
            memberId,
            role,
            serverId: currentServer?.id || ''
          })
          .pipe(
            map((server) => actions.ChangeMemberRoleSuccessAction({ server })),
            catchError(() => of(actions.ChangeMemberRoleFailureAction()))
          )
      )
    )
  );

  kickMemberAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.KickMemberAction),
      withLatestFrom(this.store$.select(serversCurrentServer)),
      switchMap(([{ memberId }, currentServer]) =>
        this.serversService
          .kickMember$({
            memberId,
            serverId: currentServer?.id || ''
          })
          .pipe(
            map((server) => actions.KickMemberSuccessAction({ server })),
            catchError(() => of(actions.KickMemberFailureAction()))
          )
      )
    )
  );

  createChannelAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.CreateChannelAction),
      withLatestFrom(this.store$.select(serversCurrentServer)),
      switchMap(([{ channelType, channelName }, currentServer]) =>
        this.serversService
          .createChannel$({
            channelName,
            channelType,
            serverId: currentServer?.id || ''
          })
          .pipe(
            map((server) => actions.CreateChannelSuccessAction({ server })),
            catchError(() => of(actions.CreateChannelFailureAction()))
          )
      )
    )
  );

  createChannelSuccessAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.CreateChannelSuccessAction),
      tap(() =>
        this.snackbarService.openSnackBar(
          'content.channel-created-successfully',
          'success'
        )
      ),
      tap(() => this.dialog.closeAll()),
      switchMap(({ server }) =>
        of(
          actions.GetServerWithChannelsMembersAndProfilesAction({
            serverId: server.id
          })
        )
      )
    )
  );

  deleteChannelAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.DeleteChannelAction),
      withLatestFrom(this.store$.select(serversCurrentServer)),
      switchMap(([{ channelId }, currentServer]) =>
        this.serversService.deleteChannel$(channelId, currentServer.id).pipe(
          map((server) =>
            actions.DeleteChannelSuccessAction({
              deletedChannelId: channelId,
              server
            })
          ),
          catchError(() => of(actions.DeleteChannelFailureAction()))
        )
      )
    )
  );

  deleteChannelSuccessAction$ = createEffect(
    () =>
      this.actions$.pipe(
        ofType(actions.DeleteChannelSuccessAction),
        tap(() =>
          this.snackbarService.openSnackBar(
            'content.channel-deleted',
            'success'
          )
        ),
        withLatestFrom(
          this.store$.select(activeChannelId),
          this.store$.select(serversCurrentServer)
        ),
        tap(([_, activeChannelId, currentServer]) => {
          const generalChannel = currentServer.channels.find(
            (channel) =>
              channel.name === 'general' && channel.id === activeChannelId
          );

          generalChannel &&
            this.router.navigate([
              'servers',
              currentServer.id,
              'channels',
              generalChannel.id
            ]);
        })
      ),
    { dispatch: false }
  );

  editChannelAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.EditChannelAction),
      withLatestFrom(this.store$.select(serversCurrentServer)),
      switchMap(([{ channelId, channelName, channelType }, currentServer]) =>
        this.serversService
          .editChannel$({
            channelId,
            serverId: currentServer.id,
            channelName,
            channelType
          })
          .pipe(
            map((server) =>
              actions.EditChannelSuccessAction({
                server
              })
            ),
            catchError(() => of(actions.EditChannelFailureAction()))
          )
      )
    )
  );

  editChannelSuccessAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.EditChannelSuccessAction),
      tap(() =>
        this.snackbarService.openSnackBar('content.channel-edited', 'success')
      ),
      tap(() => this.dialog.closeAll()),
      switchMap(({ server }) =>
        of(
          actions.GetServerWithChannelsMembersAndProfilesAction({
            serverId: server.id
          })
        )
      )
    )
  );
}
