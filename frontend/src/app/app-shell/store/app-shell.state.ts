import { ServerDef } from '../interfaces/server.interface';

export type AppShellIsLoadingKey =
  | 'create-edit-server'
  | 'get-servers'
  | 'join-server'
  | 'delete-server'
  | 'leave-server';

export interface AppShellState {
  activeServerId: string;
  servers: ServerDef[];
  isLoading: AppShellIsLoadingKey[];
}

export const initialAppShellState: AppShellState = {
  activeServerId: '',
  servers: [],
  isLoading: []
};
