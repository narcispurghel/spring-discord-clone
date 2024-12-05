import { createAction, props } from '@ngrx/store';
import { OnSubmitChatInputData } from 'src/app/shared/interfaces/chat-input.interface';
import { MessageWithMemberAndProfile } from '../../../interfaces/message.interfaces';

const channelsKey = '[CHANNELS]';

export enum ChannelsActionTypes {
  SEND_MESSAGE_ACTION = `${channelsKey} Change member role action`,
  SEND_MESSAGE_SUCCESS = `${channelsKey} Change member role success`,
  SEND_MESSAGE_ERROR = `${channelsKey} Change member role error`,

  RESET_STATE = `${channelsKey} Reset state`
}

export const SendMessageAction = createAction(
  ChannelsActionTypes.SEND_MESSAGE_ACTION,
  props<{ data: OnSubmitChatInputData }>()
);

export const SendMessageSuccessAction = createAction(
  ChannelsActionTypes.SEND_MESSAGE_SUCCESS,
  props<{ message: MessageWithMemberAndProfile }>()
);

export const SendMessageFailureAction = createAction(
  ChannelsActionTypes.SEND_MESSAGE_ERROR
);

export const ResetChannelsStateAction = createAction(
  ChannelsActionTypes.RESET_STATE
);
