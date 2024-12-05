import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';

import { AppState } from 'src/app/store/app.state';

import { OnSubmitChatInputData } from 'src/app/shared/interfaces/chat-input.interface';
import * as actions from './channels.actions';

@Injectable({ providedIn: 'root' })
export class ChannelsFacade {
  constructor(private store: Store<AppState>) {}

  sendMessage(data: OnSubmitChatInputData): void {
    this.store.dispatch(actions.SendMessageAction({ data }));
  }
}
