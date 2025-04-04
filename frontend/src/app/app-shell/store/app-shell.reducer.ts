import { createReducer, on } from '@ngrx/store';

import { AppShellState, initialAppShellState } from './app-shell.state';

import { handleReducerLoading } from 'src/app/shared/utility/store.utility';
import * as actions from './app-shell.actions';

export const appShellReducer = createReducer<AppShellState>(
  initialAppShellState,
  on(actions.CreateServerAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'create-edit-server', true)
  })),
  on(actions.CreateServerSuccessAction, (state, { server }) => ({
    ...state,
    servers: [...state.servers, server],
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-server',
      false
    )
  })),
  on(actions.CreateServerFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-server',
      false
    )
  })),

  on(actions.EditServerAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'create-edit-server', true)
  })),
  on(actions.EditServerSuccessAction, (state, { server }) => {
    const updatedServerIndex = state.servers.findIndex(
      (currentServer) => currentServer.id === server.id
    );
    const updatedServers = structuredClone(state.servers);
    updatedServers[updatedServerIndex] = server;

    return {
      ...state,
      servers: updatedServers,
      isLoading: handleReducerLoading(
        state.isLoading,
        'create-edit-server',
        false
      )
    };
  }),
  on(actions.EditServerFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(
      state.isLoading,
      'create-edit-server',
      false
    )
  })),

  on(actions.DeleteServerAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'delete-server', true)
  })),
  on(actions.DeleteServerSuccessAction, (state, { deletedServerId }) => {
    const deletedServerIndex = state.servers.findIndex(
      (currentServer) => currentServer.id === deletedServerId
    );
    const updatedServers = structuredClone(state.servers);
    updatedServers.splice(deletedServerIndex, 1);

    return {
      ...state,
      activeServerId: updatedServers.length > 0 ? updatedServers[0].id : '',
      servers: updatedServers,
      isLoading: handleReducerLoading(state.isLoading, 'delete-server', false)
    };
  }),
  on(actions.DeleteServerFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'delete-server', false)
  })),

  on(actions.LeaveServerAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'leave-server', true)
  })),
  on(actions.LeaveServerSuccessAction, (state, { leftServerId }) => {
    const leftServerIndex = state.servers.findIndex(
      (currentServer) => currentServer.id === leftServerId
    );
    const updatedServers = structuredClone(state.servers);
    updatedServers.splice(leftServerIndex, 1);

    return {
      ...state,
      activeServerId: updatedServers.length > 0 ? updatedServers[0].id : '',
      servers: updatedServers,
      isLoading: handleReducerLoading(state.isLoading, 'leave-server', false)
    };
  }),
  on(actions.LeaveServerFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'leave-server', false)
  })),

  on(actions.JoinServerAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'join-server', true)
  })),
  on(actions.JoinServerSuccessAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'join-server', false)
  })),
  on(actions.JoinServerFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'join-server', false)
  })),

  on(actions.GetServersAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'get-servers', true)
  })),
  on(actions.GetServersSuccessAction, (state, { servers }) => ({
    ...state,
    servers,
    isLoading: handleReducerLoading(state.isLoading, 'get-servers', false)
  })),
  on(actions.GetServersFailureAction, (state) => ({
    ...state,
    isLoading: handleReducerLoading(state.isLoading, 'get-servers', false)
  })),

  on(actions.SetActiveServerId, (state, { serverId }) => ({
    ...state,
    activeServerId: serverId
  }))
);
