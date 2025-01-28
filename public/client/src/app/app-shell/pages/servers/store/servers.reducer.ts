import { createReducer, on } from '@ngrx/store';

import { initialServersState, ServersState } from './servers.state';

import { handleReducerLoading } from 'src/app/shared/utility/store.utility';
import * as actions from './servers.actions';

export const serversReducer = createReducer<ServersState>(
  initialServersState,
  on(actions.GetServerWithChannelsMembersAndProfilesAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'get-server-with-channels-members-and-profiles',
      true
    )
  })),
  on(
    actions.GetServerWithChannelsMembersAndProfilesSuccessAction,
    (state, { server }) => ({
      ...state,
      currentServer: server,
      isLoading: handleReducerLoading(
        state.isLoading,
        'get-server-with-channels-members-and-profiles',
        false
      )
    })
  ),
  on(actions.GetServerWithChannelsMembersAndProfilesFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'get-server-with-channels-members-and-profiles',
      false
    )
  })),

  on(actions.GenerateNewInviteCodeAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'generate-new-invite-code',
      true
    )
  })),
  on(actions.GenerateNewInviteCodeSuccessAction, (state, { server }) => ({
    ...state,
    currentServer: {
      ...state.currentServer,
      inviteCode: server.inviteCode
    },
    isLoading: handleReducerLoading(
      state.isLoading,
      'generate-new-invite-code',
      false
    )
  })),
  on(actions.GenerateNewInviteCodeFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'generate-new-invite-code',
      false
    )
  })),

  on(actions.ChangeMemberRoleAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'change-member-role', true)
  })),
  on(actions.ChangeMemberRoleSuccessAction, (state, { server }) => ({
    ...state,
    currentServer: {
      ...state.currentServer,
      ...server
    },
    isLoading: handleReducerLoading(
      state.isLoading,
      'change-member-role',
      false
    )
  })),
  on(actions.ChangeMemberRoleFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'change-member-role',
      false
    )
  })),

  on(actions.KickMemberAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'kick-member', true)
  })),
  on(actions.KickMemberSuccessAction, (state, { server }) => ({
    ...state,
    currentServer: {
      ...state.currentServer,
      ...server
    },
    isLoading: handleReducerLoading(state.isLoading, 'kick-member', false)
  })),
  on(actions.KickMemberFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'kick-member', false)
  })),

  on(actions.CreateChannelAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      true
    )
  })),
  on(actions.CreateChannelSuccessAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      false
    )
  })),
  on(actions.CreateChannelFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      false
    )
  })),

  on(actions.DeleteChannelAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'delete-channel', true)
  })),
  on(actions.DeleteChannelSuccessAction, (state, { deletedChannelId }) => {
    const hasDeletedActiveChannel = state.activeChannelId === deletedChannelId;
    let activeChannelId = state.activeChannelId;

    if (hasDeletedActiveChannel) {
      const generalChannel = state.currentServer.channels.find(
        (channel) => channel.name === 'general'
      );

      activeChannelId = generalChannel!.id;
    }

    return {
      ...state,
      currentServer: {
        ...state.currentServer,
        channels: state.currentServer.channels.filter(
          (channel) => channel.id !== deletedChannelId
        )
      },
      activeChannelId,
      isLoading: handleReducerLoading(state.isLoading, 'delete-channel', false)
    };
  }),
  on(actions.DeleteChannelFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'delete-channel', false)
  })),

  on(actions.EditChannelAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      true
    )
  })),
  on(actions.EditChannelSuccessAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      false
    )
  })),
  on(actions.EditChannelFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-channel',
      false
    )
  })),

  on(actions.SetActiveChannelId, (state, { channelId }) => ({
    ...state,
    activeChannelId: channelId
  })),
  on(actions.SetActiveConversationId, (state, { conversationId }) => ({
    ...state,
    activeMemberConversationId: conversationId
  }))
);
