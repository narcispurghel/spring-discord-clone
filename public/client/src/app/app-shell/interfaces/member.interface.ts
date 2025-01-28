export enum MemberRole {
  ADMIN = 'ADMIN',
  MODERATOR = 'MODERATOR',
  GUEST = 'GUEST'
}

export interface MemberDto {
  createdAt: string;
  id: string;
  profileId: string;
  role: MemberRole;
  serverId: string;
  updatedAt: string;
}

export interface MemberDef {
  createdAt: string;
  id: string;
  profileId: string;
  role: MemberRole;
  serverId: string;
  updatedAt: string;
}
