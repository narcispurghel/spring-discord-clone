import { createFeatureSelector, createSelector } from '@ngrx/store';
import { storeConstants } from 'src/app/shared/constants/store.constant';
import { AuthState } from './auth.state';

export const authState = createFeatureSelector<AuthState>(
  storeConstants.authState
);

export const authUser = createSelector(
  authState,
  (state: AuthState) => state.user
);
