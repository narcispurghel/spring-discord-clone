import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';

export interface ChangeMemberRolePayload {
  memberId: string;
  serverId: string;
  role: MemberRole;
}

export interface KickMemberPayload {
  memberId: string;
  serverId: string;
}

export interface CreateChannelPayload {
  serverId: string;
  channelName: string;
  channelType: ChannelType;
}

export interface EditChannelPayload {
  channelId: string;
  serverId: string;
  channelName: string;
  channelType: ChannelType;
}
