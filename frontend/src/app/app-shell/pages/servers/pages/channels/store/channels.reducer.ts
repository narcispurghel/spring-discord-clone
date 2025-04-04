import { createReducer, on } from '@ngrx/store';

import { ChannelsState, initialChannelsState } from './channels.state';

import { handleReducerLoading } from 'src/app/shared/utility/store.utility';
import * as actions from './channels.actions';

export const channelsReducer = createReducer<ChannelsState>(
  initialChannelsState,
  on(actions.SendMessageAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'send-message', true)
  })),
  on(actions.SendMessageSuccessAction, (state, { message }) => ({
    ...state,
    lastSentMessage: message,
    hasFailedSendingLastMessage: false,
    isLoading: handleReducerLoading(state.isLoading, 'send-message', false)
  })),
  on(actions.SendMessageFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'send-message', false),
    hasFailedSendingLastMessage: true
  })),

  on(actions.ResetChannelsStateAction, (state) => ({
    ...state,
    ...initialChannelsState
  }))
);
