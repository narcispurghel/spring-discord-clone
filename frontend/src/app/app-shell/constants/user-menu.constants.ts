export type UserMenuItemType = 'update-profile' | 'logout';

interface UserMenuOptionDef {
  textKey: string;
  iconType: string;
  type: UserMenuItemType;
}

export const USER_MENU_OPTIONS: UserMenuOptionDef[] = [
  {
    iconType: 'person_edit',
    textKey: 'content.profile',
    type: 'update-profile'
  },
  {
    iconType: 'logout',
    textKey: 'content.logout',
    type: 'logout'
  }
];
