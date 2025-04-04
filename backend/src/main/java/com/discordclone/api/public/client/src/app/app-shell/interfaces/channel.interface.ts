export enum ChannelType {
  TEXT = 'TEXT',
  AUDIO = 'AUDIO',
  VIDEO = 'VIDEO'
}

export interface ChannelDto {
  createdAt: string;
  id: string;
  name: string;
  profileId: string;
  serverId: string;
  type: ChannelType;
  updatedAt: string;
}

export interface ChannelDef {
  createdAt: string;
  id: string;
  name: string;
  profileId: string;
  serverId: string;
  type: ChannelType;
  updatedAt: string;
}
