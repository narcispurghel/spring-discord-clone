import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';

interface RoleIconMap
  extends Record<MemberRole, { class: string; iconType: string } | null> {}

export const ROLE_ICON_MAP: RoleIconMap = {
  [MemberRole.ADMIN]: {
    class: 'mdi-medium ml-2 text-rose-500',
    iconType: 'gpp_maybe'
  },
  [MemberRole.MODERATOR]: {
    class: 'mdi-medium ml-2 text-indigo-500',
    iconType: 'verified_user'
  },
  [MemberRole.GUEST]: null
};
