import { Injectable } from '@angular/core';
import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { CHANNEL_ICON_MAP } from 'src/app/shared/constants/channel-icon-map.constant';
import { ROLE_ICON_MAP } from 'src/app/shared/constants/role-icon-map.constant';
import {
  ServerSearchAndSectionData,
  ServerWithChannelsMembersAndProfilesDef
} from '../interfaces/servers.interfaces';

@Injectable()
export class ServerSidebarService {
  getSearchData(
    profileId: string,
    server: ServerWithChannelsMembersAndProfilesDef
  ): ServerSearchAndSectionData {
    const textChannels = server?.channels.filter(
      (channel) => channel.type === ChannelType.TEXT
    );
    const audioChannels = server?.channels.filter(
      (channel) => channel.type === ChannelType.AUDIO
    );
    const videoChannels = server?.channels.filter(
      (channel) => channel.type === ChannelType.VIDEO
    );
    const members = server?.members.filter(
      (member) => member.profileId !== profileId
    );

    return {
      audioChannels,
      members,
      textChannels,
      videoChannels,
      searchData: [
        {
          labelKey: 'content.text-channels',
          type: 'channel',
          data: textChannels?.map((channel) => ({
            id: channel.id,
            icon: CHANNEL_ICON_MAP[channel.type],
            name: channel.name
          }))
        },
        {
          labelKey: 'content.voice-channels',
          type: 'channel',
          data: audioChannels?.map((channel) => ({
            id: channel.id,
            icon: CHANNEL_ICON_MAP[channel.type],
            name: channel.name
          }))
        },
        {
          labelKey: 'content.video-channels',
          type: 'channel',
          data: videoChannels?.map((channel) => ({
            id: channel.id,
            icon: CHANNEL_ICON_MAP[channel.type],
            name: channel.name
          }))
        },
        {
          labelKey: 'content.members',
          type: 'member',
          data: members?.map((member) => ({
            id: member.id,
            icon: ROLE_ICON_MAP[member.role],
            name: member.profile.name
          }))
        }
      ]
    };
  }
}
