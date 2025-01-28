import { MessageWithMemberAndProfile } from '../../../interfaces/message.interfaces';

export type ChannelsIsLoadingKey = 'send-message';

export interface ChannelsState {
  isLoading: ChannelsIsLoadingKey[];
  lastSentMessage: MessageWithMemberAndProfile | null;
  hasFailedSendingLastMessage: boolean;
}

export const initialChannelsState: ChannelsState = {
  lastSentMessage: null,
  hasFailedSendingLastMessage: false,
  isLoading: []
};
