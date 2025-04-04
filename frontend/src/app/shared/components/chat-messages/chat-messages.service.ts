import { Injectable } from '@angular/core';
import { Socket } from 'ngx-socket-io';
import { MessageWithMemberAndProfile } from 'src/app/app-shell/pages/servers/interfaces/message.interfaces';

@Injectable()
export class ChatMessagesService {
  constructor(private socket: Socket) {}

  subscribeMessages(addKey: string): void {
    this.socket.on(addKey, (message: MessageWithMemberAndProfile) => {
      console.log(message);
    });
  }

  unsubscribe(addKey: string, updateKey: string): void {
    this.socket.off(addKey);
    this.socket.off(updateKey);
  }
}
