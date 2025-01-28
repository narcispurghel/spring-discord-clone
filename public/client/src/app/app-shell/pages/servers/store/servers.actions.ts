import { createAction, props } from '@ngrx/store';
import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { ServerDef } from 'src/app/app-shell/interfaces/server.interface';
import {
  ServerWithChannelsMembersAndProfilesDef,
  ServerWithMembersAndProfilesDef
} from '../interfaces/servers.interfaces';

const serversKey = '[SERVERS]';

export enum ServersActionTypes {
  GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_ACTION = `${serversKey} Get current server data action`,
  GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_SUCCESS = `${serversKey} Get current server data success`,
  GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_ERROR = `${serversKey} Get current server data error`,

  CHANGE_MEMBER_ROLE_ACTION = `${serversKey} Change member role action`,
  CHANGE_MEMBER_ROLE_SUCCESS = `${serversKey} Change member role success`,
  CHANGE_MEMBER_ROLE_ERROR = `${serversKey} Change member role error`,

  CREATE_CHANNEL_ACTION = `${serversKey} Create channel action`,
  CREATE_CHANNEL_SUCCESS = `${serversKey} Create channel success`,
  CREATE_CHANNEL_ERROR = `${serversKey} Create channel error`,

  EDIT_CHANNEL_ACTION = `${serversKey} Edit channel action`,
  EDIT_CHANNEL_SUCCESS = `${serversKey} Edit channel success`,
  EDIT_CHANNEL_ERROR = `${serversKey} Edit channel error`,

  DELETE_CHANNEL_ACTION = `${serversKey} Delete channel action`,
  DELETE_CHANNEL_SUCCESS = `${serversKey} Delete channel success`,
  DELETE_CHANNEL_ERROR = `${serversKey} Delete channel error`,

  KICK_MEMBER_ACTION = `${serversKey} Kick member from server action`,
  KICK_MEMBER_SUCCESS = `${serversKey} Kick member from server success`,
  KICK_MEMBER_ERROR = `${serversKey} Kick member from server error`,

  GENERATE_NEW_INVITE_CODE_ACTION = `${serversKey} Generate new invite code action`,
  GENERATE_NEW_INVITE_CODE_SUCCESS = `${serversKey} Generate new invite code success`,
  GENERATE_NEW_INVITE_CODE_ERROR = `${serversKey} Generate new invite code error`,

  SET_ACTIVE_CHANNEL_ID = `${serversKey} Set active channel id`,
  SET_ACTIVE_CONVERSATION_ID = `${serversKey} Set active conversation id`
}

export const GetServerWithChannelsMembersAndProfilesAction = createAction(
  ServersActionTypes.GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_ACTION,
  props<{ serverId: string }>()
);

export const GetServerWithChannelsMembersAndProfilesSuccessAction =
  createAction(
    ServersActionTypes.GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_SUCCESS,
    props<{ server: ServerWithChannelsMembersAndProfilesDef }>()
  );

export const GetServerWithChannelsMembersAndProfilesFailureAction =
  createAction(
    ServersActionTypes.GET_SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILE_ERROR
  );

export const ChangeMemberRoleAction = createAction(
  ServersActionTypes.CHANGE_MEMBER_ROLE_ACTION,
  props<{ memberId: string; role: MemberRole }>()
);

export const ChangeMemberRoleSuccessAction = createAction(
  ServersActionTypes.CHANGE_MEMBER_ROLE_SUCCESS,
  props<{ server: ServerWithMembersAndProfilesDef }>()
);

export const ChangeMemberRoleFailureAction = createAction(
  ServersActionTypes.CHANGE_MEMBER_ROLE_ERROR
);

export const CreateChannelAction = createAction(
  ServersActionTypes.CREATE_CHANNEL_ACTION,
  props<{ channelName: string; channelType: ChannelType }>()
);

export const CreateChannelSuccessAction = createAction(
  ServersActionTypes.CREATE_CHANNEL_SUCCESS,
  props<{ server: ServerDef }>()
);

export const CreateChannelFailureAction = createAction(
  ServersActionTypes.CREATE_CHANNEL_ERROR
);

export const EditChannelAction = createAction(
  ServersActionTypes.EDIT_CHANNEL_ACTION,
  props<{ channelName: string; channelType: ChannelType; channelId: string }>()
);

export const EditChannelSuccessAction = createAction(
  ServersActionTypes.EDIT_CHANNEL_SUCCESS,
  props<{ server: ServerDef }>()
);

export const EditChannelFailureAction = createAction(
  ServersActionTypes.EDIT_CHANNEL_ERROR
);

export const DeleteChannelAction = createAction(
  ServersActionTypes.DELETE_CHANNEL_ACTION,
  props<{ channelId: string }>()
);

export const DeleteChannelSuccessAction = createAction(
  ServersActionTypes.DELETE_CHANNEL_SUCCESS,
  props<{ deletedChannelId: string; server: ServerDef }>()
);

export const DeleteChannelFailureAction = createAction(
  ServersActionTypes.DELETE_CHANNEL_ERROR
);

export const KickMemberAction = createAction(
  ServersActionTypes.KICK_MEMBER_ACTION,
  props<{ memberId: string }>()
);

export const KickMemberSuccessAction = createAction(
  ServersActionTypes.KICK_MEMBER_SUCCESS,
  props<{ server: ServerWithMembersAndProfilesDef }>()
);

export const KickMemberFailureAction = createAction(
  ServersActionTypes.KICK_MEMBER_ERROR
);

export const GenerateNewInviteCodeAction = createAction(
  ServersActionTypes.GENERATE_NEW_INVITE_CODE_ACTION,
  props<{ serverId: string }>()
);

export const GenerateNewInviteCodeSuccessAction = createAction(
  ServersActionTypes.GENERATE_NEW_INVITE_CODE_SUCCESS,
  props<{ server: ServerDef }>()
);

export const GenerateNewInviteCodeFailureAction = createAction(
  ServersActionTypes.GENERATE_NEW_INVITE_CODE_ERROR
);

export const SetActiveChannelId = createAction(
  ServersActionTypes.SET_ACTIVE_CHANNEL_ID,
  props<{ channelId: string }>()
);

export const SetActiveConversationId = createAction(
  ServersActionTypes.SET_ACTIVE_CONVERSATION_ID,
  props<{ conversationId: string }>()
);
