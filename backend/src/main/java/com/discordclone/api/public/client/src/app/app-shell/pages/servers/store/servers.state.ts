import { ServerWithChannelsMembersAndProfilesDef } from '../interfaces/servers.interfaces';

export type ServersIsLoadingKey =
  | 'get-server-with-channels-members-and-profiles'
  | 'generate-new-invite-code'
  | 'change-member-role'
  | 'kick-member'
  | 'create-edit-channel'
  | 'delete-channel';

export interface ServersState {
  activeChannelId: string;
  activeMemberConversationId: string;
  activeServerId: string;
  currentServer: ServerWithChannelsMembersAndProfilesDef;
  isLoading: ServersIsLoadingKey[];
}

export const initialServersState: ServersState = {
  activeServerId: '',
  activeMemberConversationId: '',
  activeChannelId: '',
  currentServer: null!,
  isLoading: []
};
