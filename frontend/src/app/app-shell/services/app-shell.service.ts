import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL_CONSTANTS } from 'src/app/shared/constants/api-urls.constants';
import { CreateEditServerDto } from '../interfaces/create-edit-server.interface';
import { ServerDef, ServerDto } from '../interfaces/server.interface';

@Injectable({ providedIn: 'root' })
export class AppShellService {
  constructor(private http: HttpClient) {}

  getServers$(): Observable<ServerDef[]> {
    return this.http.get<ServerDto[]>(API_URL_CONSTANTS.SERVERS.SERVERS);
  }

  editServer$({
    serverId,
    data
  }: {
    serverId: string;
    data: CreateEditServerDto;
  }): Observable<ServerDef> {
    return this.http.patch<ServerDto>(
      API_URL_CONSTANTS.SERVERS.EDIT_SERVER.replace('{serverId}', serverId),
      data
    );
  }

  createServer$(data: CreateEditServerDto): Observable<ServerDef> {
    return this.http.post<ServerDto>(
      API_URL_CONSTANTS.SERVERS.CREATE_SERVER,
      data
    );
  }

  deleteServer$(serverId: string): Observable<{ message: string }> {
    return this.http.delete<{ message: string }>(
      API_URL_CONSTANTS.SERVERS.DELETE_SERVER.replace('{serverId}', serverId)
    );
  }

  leaveServer$(serverId: string): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(
      API_URL_CONSTANTS.SERVERS.LEAVE_SERVER.replace('{serverId}', serverId)
    );
  }

  joinServer$(inviteCode: string): Observable<any> {
    return this.http.get(
      API_URL_CONSTANTS.SERVERS.JOIN_SERVER.replace('{inviteCode}', inviteCode)
    );
  }
}
