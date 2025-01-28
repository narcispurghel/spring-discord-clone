import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { Store } from '@ngrx/store';
import { catchError, map, of, switchMap, withLatestFrom } from 'rxjs';
import { activeServerId } from 'src/app/app-shell/store/app-shell.selectors';
import { AppState } from 'src/app/store/app.state';
import { activeChannelId } from '../../../store/servers.selectors';
import { ChannelsService } from '../services/channels.service';
import * as actions from './channels.actions';

@Injectable({ providedIn: 'root' })
export class ChannelsStoreEffects {
  constructor(
    private actions$: Actions,
    private store$: Store<AppState>,
    private channelsService: ChannelsService
  ) {}

  sendMessageAction$ = createEffect(() =>
    this.actions$.pipe(
      ofType(actions.SendMessageAction),
      withLatestFrom(
        this.store$.select(activeServerId),
        this.store$.select(activeChannelId)
      ),
      switchMap(([{ data }, serverId, channelId]) =>
        this.channelsService
          .postChannelMessage({ data, channelId, serverId })
          .pipe(
            map((message) => actions.SendMessageSuccessAction({ message })),
            catchError(() => of(actions.SendMessageFailureAction()))
          )
      )
    )
  );
}
