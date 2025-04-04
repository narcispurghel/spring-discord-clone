export const API_URL_CONSTANTS = {
  AUTH: {
    REGISTER: 'auth/register',
    LOGIN: 'auth/login',
    LOGOUT: 'auth/logout',
    REFRESH: 'auth/refresh'
  },
  CHANNELS: {
    CHANNELS: 'channels',
    DELETE: 'channels/{channelId}/{serverId}',
    EDIT: 'channels/{channelId}/{serverId}',
    POST_MESSAGE: 'messages/channel-message',
  },
  SERVERS: {
    CREATE_SERVER: 'servers',
    EDIT_SERVER: 'servers/{serverId}',
    CHANGE_MEMBER_ROLE: 'servers/{serverId}/{memberId}',
    JOIN_SERVER: 'servers/invite/{inviteCode}',
    SERVERS: 'servers',
    DELETE_SERVER: 'servers/{serverId}',
    LEAVE_SERVER: 'servers/{serverId}/leave',
    SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILES:
      'servers/{serverId}/server-with-channels-members-and-profiles',
    GENERATE_NEW_INVITE_CODE: 'servers/{serverId}/invite-code'
  },
  USER: {
    UPDATE_PROFILE: 'user/update-profile'
  },
  UPLOAD: {
    CLIENT_SIDE_UPLOADING: 'upload/client-side-uploading'
  }
};
