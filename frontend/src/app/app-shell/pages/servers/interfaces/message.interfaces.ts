import { ChannelDef } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberDef } from 'src/app/app-shell/interfaces/member.interface';
import { ProfileDef } from 'src/app/app-shell/interfaces/profile.interface';

export interface MessageDef {
  id: string;
  content: string;

  fileUrl?: string;

  memberId: string;
  member?: MemberDef;

  channelId: string;
  channel?: ChannelDef;

  deleted: boolean;

  createdAt: string;
  updatedAt: string;
}

export type MessageWithMemberAndProfile = MessageDef & {
  member: MemberDef & {
    profile: ProfileDef;
  };
};
