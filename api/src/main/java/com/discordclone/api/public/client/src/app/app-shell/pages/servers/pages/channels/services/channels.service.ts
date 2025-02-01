import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL_CONSTANTS } from 'src/app/shared/constants/api-urls.constants';
import { OnSubmitChatInputData } from 'src/app/shared/interfaces/chat-input.interface';
import { MessageWithMemberAndProfile } from '../../../interfaces/message.interfaces';

@Injectable({
  providedIn: 'root'
})
export class ChannelsService {
  private http = inject(HttpClient);

  postChannelMessage({
    data,
    channelId,
    serverId
  }: {
    data: OnSubmitChatInputData;
    channelId: string;
    serverId: string;
  }): Observable<MessageWithMemberAndProfile> {
    return this.http.post<MessageWithMemberAndProfile>(
      API_URL_CONSTANTS.CHANNELS.POST_MESSAGE,
      data,
      {
        params: { channelId, serverId }
      }
    );
  }
}
