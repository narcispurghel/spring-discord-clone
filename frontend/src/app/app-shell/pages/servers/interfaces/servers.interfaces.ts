import {
  ChannelDef,
  ChannelDto
} from 'src/app/app-shell/interfaces/channel.interface';
import {
  MemberDef,
  MemberDto
} from 'src/app/app-shell/interfaces/member.interface';
import {
  ProfileDef,
  ProfileDto
} from 'src/app/app-shell/interfaces/profile.interface';
import {
  ServerDef,
  ServerDto
} from 'src/app/app-shell/interfaces/server.interface';

export type ServerWithChannelsMembersAndProfilesDto = ServerDto & {
  channels: ChannelDto[];
  members: (MemberDto & { profile: ProfileDto })[];
};

export type MemberWithProfileDef = MemberDef & { profile: ProfileDef };

export type ServerWithChannelsMembersAndProfilesDef = ServerDef & {
  channels: ChannelDef[];
  members: MemberWithProfileDef[];
};

export type ServerWithMembersAndProfilesDto = Omit<
  ServerWithChannelsMembersAndProfilesDto,
  'channels'
>;

export type ServerWithMembersAndProfilesDef = Omit<
  ServerWithChannelsMembersAndProfilesDef,
  'channels'
>;

export type ServerSearchDataType = 'channel' | 'member';

export interface ServerSearchData {
  labelKey: string;
  type: ServerSearchDataType;
  data:
    | {
        icon: { class?: string; iconType: string } | null;
        name: string;
        id: string;
      }[]
    | undefined;
}

export interface ServerSearchAndSectionData {
  textChannels: ChannelDef[];
  audioChannels: ChannelDef[];
  videoChannels: ChannelDef[];
  members: MemberWithProfileDef[];
  searchData: ServerSearchData[];
}
