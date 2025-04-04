import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';

export type ServerHeaderOptionType =
  | 'invite-people'
  | 'server-settings'
  | 'manage-members'
  | 'create-channel'
  | 'delete-server'
  | 'leave-server';

const sharedClasses =
  'cursor-pointer rounded-lg px-3 py-2 text-sm hover:bg-black/20 dark:hover:!bg-white/20';

const sharedIconClasses = '!text-gray-500 dark:!text-gray-400';

interface ServerHeaderOption {
  canAccess: MemberRole[];
  iconClass: string;
  iconType: string;
  type: ServerHeaderOptionType;
  class: string;
  textKey: string;
}

export const serverHeaderOptions: ServerHeaderOption[] = [
  {
    class: `${sharedClasses} !dark:text-indigo-400 !text-indigo-600`,
    iconClass: '!text-indigo-600',
    canAccess: [MemberRole.ADMIN],
    textKey: 'content.invite-people',
    type: 'invite-people',
    iconType: 'person_add'
  },
  {
    class: `${sharedClasses} !text-gray-500 dark:!text-gray-400`,
    canAccess: [MemberRole.ADMIN],
    iconClass: sharedIconClasses,
    textKey: 'content.server-settings',
    type: 'server-settings',
    iconType: 'settings'
  },
  {
    class: `${sharedClasses} !text-gray-500 dark:!text-gray-400`,
    canAccess: [MemberRole.ADMIN],
    iconClass: sharedIconClasses,
    textKey: 'content.manage-members',
    type: 'manage-members',
    iconType: 'group'
  },
  {
    class: `${sharedClasses} !text-gray-500 dark:!text-gray-400`,
    canAccess: [MemberRole.MODERATOR, MemberRole.ADMIN],
    iconClass: sharedIconClasses,
    textKey: 'content.create-channel',
    type: 'create-channel',
    iconType: 'add_circle'
  },
  {
    class: `${sharedClasses} !text-rose-500`,
    canAccess: [MemberRole.ADMIN],
    iconClass: '!text-rose-500',
    textKey: 'content.delete-server',
    type: 'delete-server',
    iconType: 'delete'
  },
  {
    class: `${sharedClasses} !text-rose-500`,
    canAccess: [MemberRole.GUEST, MemberRole.MODERATOR],
    iconClass: '!text-rose-500',
    textKey: 'content.leave-server',
    type: 'leave-server',
    iconType: 'logout'
  }
];
