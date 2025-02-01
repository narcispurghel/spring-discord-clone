import { UserDef } from '../interfaces/auth.interface';

export type AuthIsLoadingKey =
  | 'login'
  | 'register'
  | 'logout'
  | 'update-profile';

export interface AuthState {
  isLoading: AuthIsLoadingKey[];
  user: UserDef | null;
}

export const initialAuthState: AuthState = {
  user: null,
  isLoading: []
};
