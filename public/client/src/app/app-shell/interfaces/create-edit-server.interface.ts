import { ServerDef } from './server.interface';

export interface CreateEditServerModalData {
  server?: ServerDef;
}

export interface CreateEditServerDto {
  serverName: string;
  serverImage: string;
}
