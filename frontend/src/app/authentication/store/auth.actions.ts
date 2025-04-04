import { createAction, props } from '@ngrx/store';
import {
  LoginFields,
  RegisterFields,
  UpdateProfileDto,
  UserDef
} from '../interfaces/auth.interface';

const authKey = '[AUTH]';

export enum AuthActionTypes {
  REGISTER_REQUEST_ACTION = `${authKey} Register request action`,
  REGISTER_SUCCESS_ACTION = `${authKey} Register success action`,
  REGISTER_FAILURE_ACTION = `${authKey} Register failure action`,

  LOGIN_REQUEST_ACTION = `${authKey} Login request action`,
  LOGIN_SUCCESS_ACTION = `${authKey} Login success action`,
  LOGIN_FAILURE_ACTION = `${authKey} Login failure action`,

  LOGOUT_REQUEST_ACTION = `${authKey} LOGOUT request action`,
  LOGOUT_SUCCESS_ACTION = `${authKey} LOGOUT success action`,
  LOGOUT_FAILURE_ACTION = `${authKey} LOGOUT failure action`,

  UPDATE_PROFILE_REQUEST_ACTION = `${authKey} UPDATE_PROFILE request action`,
  UPDATE_PROFILE_SUCCESS_ACTION = `${authKey} UPDATE_PROFILE success action`,
  UPDATE_PROFILE_FAILURE_ACTION = `${authKey} UPDATE_PROFILE failure action`
}

export const RegisterRequestAction = createAction(
  AuthActionTypes.REGISTER_REQUEST_ACTION,
  props<{ registerFields: RegisterFields }>()
);

export const RegisterSuccessAction = createAction(
  AuthActionTypes.REGISTER_SUCCESS_ACTION,
  props<{ user: UserDef }>()
);

export const RegisterFailureAction = createAction(
  AuthActionTypes.REGISTER_FAILURE_ACTION
);

export const LoginRequestAction = createAction(
  AuthActionTypes.LOGIN_REQUEST_ACTION,
  props<{ loginFields: LoginFields }>()
);

export const LoginSuccessAction = createAction(
  AuthActionTypes.LOGIN_SUCCESS_ACTION,
  props<{ user: UserDef }>()
);

export const LoginFailureAction = createAction(
  AuthActionTypes.LOGIN_FAILURE_ACTION
);

export const LogoutRequestAction = createAction(
  AuthActionTypes.LOGOUT_REQUEST_ACTION
);

export const LogoutSuccessAction = createAction(
  AuthActionTypes.LOGOUT_SUCCESS_ACTION,
  props<{ messageKey?: string }>()
);

export const LogoutFailureAction = createAction(
  AuthActionTypes.LOGOUT_FAILURE_ACTION
);

export const UpdateProfileRequestAction = createAction(
  AuthActionTypes.UPDATE_PROFILE_REQUEST_ACTION,
  props<{ data: UpdateProfileDto }>()
);

export const UpdateProfileSuccessAction = createAction(
  AuthActionTypes.UPDATE_PROFILE_SUCCESS_ACTION,
  props<{ user: UserDef }>()
);

export const UpdateProfileFailureAction = createAction(
  AuthActionTypes.UPDATE_PROFILE_FAILURE_ACTION
);
