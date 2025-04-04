import { createReducer, on } from '@ngrx/store';

import { AuthState, initialAuthState } from './auth.state';

import * as actions from './auth.actions';
import { handleReducerLoading } from 'src/app/shared/utility/store.utility';

export const authReducer = createReducer<AuthState>(
  initialAuthState,
  on(actions.RegisterRequestAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'register', true)
  })),
  on(actions.RegisterSuccessAction, (state, { user }) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'register', false),
    user
  })),
  on(actions.RegisterFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'register', false),
    user: null
  })),

  on(actions.LoginRequestAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'login', true)
  })),
  on(actions.LoginSuccessAction, (state, { user }) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'login', false),
    user
  })),
  on(actions.LoginFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'login', false)
  })),

  on(actions.LogoutRequestAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'logout', true)
  })),
  on(actions.LogoutSuccessAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'logout', false),
    user: null
  })),
  on(actions.LogoutFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'logout', false)
  })),

  on(actions.UpdateProfileRequestAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'update-profile', true)
  })),
  on(actions.UpdateProfileSuccessAction, (state, { user }) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'update-profile', false),
    user
  })),
  on(actions.UpdateProfileFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'update-profile', false)
  })),
);
