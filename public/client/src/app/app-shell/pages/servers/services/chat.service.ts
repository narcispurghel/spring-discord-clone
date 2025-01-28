import { Injectable } from '@angular/core';

interface SendMessageProps {
  message: string;
  query: Record<string, any>;
  apiUrl: string;
}

@Injectable()
export class ChatService {
  sendChannelMessage() {}
}
