import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  ServerDef,
  ServerDto
} from 'src/app/app-shell/interfaces/server.interface';
import { API_URL_CONSTANTS } from 'src/app/shared/constants/api-urls.constants';
import {
  ChangeMemberRolePayload,
  CreateChannelPayload,
  EditChannelPayload,
  KickMemberPayload
} from '../interfaces/manage-members-modal.interface';
import {
  ServerWithChannelsMembersAndProfilesDef,
  ServerWithChannelsMembersAndProfilesDto,
  ServerWithMembersAndProfilesDef,
  ServerWithMembersAndProfilesDto
} from '../interfaces/servers.interfaces';

@Injectable()
export class ServersService {
  constructor(private http: HttpClient) {}

  changeMemberRole$({
    memberId,
    serverId,
    role
  }: ChangeMemberRolePayload): Observable<ServerWithMembersAndProfilesDef> {
    const url = `${API_URL_CONSTANTS.SERVERS.CHANGE_MEMBER_ROLE.replace(
      '{serverId}',
      serverId
    ).replace('{memberId}', memberId)}`;

    return this.http.patch<ServerWithMembersAndProfilesDto>(url, { role });
  }

  kickMember$({
    memberId,
    serverId
  }: KickMemberPayload): Observable<ServerWithMembersAndProfilesDef> {
    const url = `${API_URL_CONSTANTS.SERVERS.CHANGE_MEMBER_ROLE.replace(
      '{serverId}',
      serverId
    ).replace('{memberId}', memberId)}`;

    return this.http.delete<ServerWithMembersAndProfilesDto>(url);
  }

  createChannel$({
    serverId,
    channelType,
    channelName
  }: CreateChannelPayload): Observable<ServerWithMembersAndProfilesDef> {
    const url = `${API_URL_CONSTANTS.CHANNELS.CHANNELS}?serverId=${serverId}`;

    return this.http.post<ServerWithMembersAndProfilesDto>(url, {
      channelName,
      channelType
    });
  }

  deleteChannel$(channelId: string, serverId: string): Observable<ServerDef> {
    return this.http.delete<ServerDto>(
      API_URL_CONSTANTS.CHANNELS.DELETE.replace(
        '{channelId}',
        channelId
      ).replace('{serverId}', serverId)
    );
  }

  editChannel$({
    channelId,
    channelName,
    channelType,
    serverId
  }: EditChannelPayload): Observable<ServerDef> {
    return this.http.patch<ServerDto>(
      API_URL_CONSTANTS.CHANNELS.DELETE.replace(
        '{channelId}',
        channelId
      ).replace('{serverId}', serverId),
      { channelName, channelType }
    );
  }

  generateNewInviteCode$(serverId: string): Observable<ServerDto> {
    return this.http.get<ServerDto>(
      API_URL_CONSTANTS.SERVERS.GENERATE_NEW_INVITE_CODE.replace(
        '{serverId}',
        serverId
      )
    );
  }

  getServerWithChannelsMembersAndProfiles$(
    serverId: string
  ): Observable<ServerWithChannelsMembersAndProfilesDef> {
    return this.http.get<ServerWithChannelsMembersAndProfilesDto>(
      API_URL_CONSTANTS.SERVERS.SERVER_WITH_CHANNELS_MEMBERS_AND_PROFILES.replace(
        '{serverId}',
        serverId
      )
    );
  }
}
