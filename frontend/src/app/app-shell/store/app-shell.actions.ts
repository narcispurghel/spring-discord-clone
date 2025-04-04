import { HttpErrorResponse } from '@angular/common/http';
import { createAction, props } from '@ngrx/store';
import { CreateEditServerDto } from '../interfaces/create-edit-server.interface';
import { ServerDef } from '../interfaces/server.interface';

const appShellKey = '[APP_SHELL]';

export enum AppShellActionTypes {
  CREATE_SERVER_ACTION = `${appShellKey} Create server action`,
  CREATE_SERVER_SUCCESS = `${appShellKey} Create server success`,
  CREATE_SERVER_ERROR = `${appShellKey} Create server error`,

  EDIT_SERVER_ACTION = `${appShellKey} Edit server action`,
  EDIT_SERVER_SUCCESS = `${appShellKey} Edit server success`,
  EDIT_SERVER_ERROR = `${appShellKey} Edit server error`,

  DELETE_SERVER_ACTION = `${appShellKey} Delete server action`,
  DELETE_SERVER_SUCCESS = `${appShellKey} Delete server success`,
  DELETE_SERVER_ERROR = `${appShellKey} Delete server error`,

  LEAVE_SERVER_ACTION = `${appShellKey} Leave server action`,
  LEAVE_SERVER_SUCCESS = `${appShellKey} Leave server success`,
  LEAVE_SERVER_ERROR = `${appShellKey} Leave server error`,

  GET_SERVERS_ACTION = `${appShellKey} GET servers action`,
  GET_SERVERS_SUCCESS = `${appShellKey} GET servers success`,
  GET_SERVERS_ERROR = `${appShellKey} GET servers error`,

  JOIN_SERVER_ACTION = `${appShellKey} Join server action`,
  JOIN_SERVER_SUCCESS = `${appShellKey} Join server success`,
  JOIN_SERVER_ERROR = `${appShellKey} Join server error`,

  SET_ACTIVE_SERVER_ID = `${appShellKey} Set active server id`
}

export const CreateServerAction = createAction(
  AppShellActionTypes.CREATE_SERVER_ACTION,
  props<{ data: CreateEditServerDto }>()
);

export const CreateServerSuccessAction = createAction(
  AppShellActionTypes.CREATE_SERVER_SUCCESS,
  props<{ server: ServerDef }>()
);

export const CreateServerFailureAction = createAction(
  AppShellActionTypes.CREATE_SERVER_ERROR
);

export const EditServerAction = createAction(
  AppShellActionTypes.EDIT_SERVER_ACTION,
  props<{ serverId: string; data: CreateEditServerDto }>()
);

export const EditServerSuccessAction = createAction(
  AppShellActionTypes.EDIT_SERVER_SUCCESS,
  props<{ server: ServerDef }>()
);

export const EditServerFailureAction = createAction(
  AppShellActionTypes.EDIT_SERVER_ERROR
);

export const DeleteServerAction = createAction(
  AppShellActionTypes.DELETE_SERVER_ACTION,
  props<{ serverId: string }>()
);

export const DeleteServerSuccessAction = createAction(
  AppShellActionTypes.DELETE_SERVER_SUCCESS,
  props<{ deletedServerId: string }>()
);

export const DeleteServerFailureAction = createAction(
  AppShellActionTypes.DELETE_SERVER_ERROR
);

export const LeaveServerAction = createAction(
  AppShellActionTypes.LEAVE_SERVER_ACTION,
  props<{ serverId: string }>()
);

export const LeaveServerSuccessAction = createAction(
  AppShellActionTypes.LEAVE_SERVER_SUCCESS,
  props<{ leftServerId: string }>()
);

export const LeaveServerFailureAction = createAction(
  AppShellActionTypes.LEAVE_SERVER_ERROR
);

export const JoinServerAction = createAction(
  AppShellActionTypes.JOIN_SERVER_ACTION,
  props<{ inviteCode: string }>()
);

export const JoinServerSuccessAction = createAction(
  AppShellActionTypes.JOIN_SERVER_SUCCESS
);

export const JoinServerFailureAction = createAction(
  AppShellActionTypes.JOIN_SERVER_ERROR,
  props<{ error: HttpErrorResponse }>()
);

export const GetServersAction = createAction(
  AppShellActionTypes.GET_SERVERS_ACTION
);

export const GetServersSuccessAction = createAction(
  AppShellActionTypes.GET_SERVERS_SUCCESS,
  props<{ servers: ServerDef[] }>()
);

export const GetServersFailureAction = createAction(
  AppShellActionTypes.GET_SERVERS_ERROR
);

export const SetActiveServerId = createAction(
  AppShellActionTypes.SET_ACTIVE_SERVER_ID,
  props<{ serverId: string }>()
);
