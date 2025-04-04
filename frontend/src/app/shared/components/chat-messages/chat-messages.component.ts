import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges
} from '@angular/core';
import { ChatMessagesService } from './chat-messages.service';

@Component({
  selector: 'app-chat-messages',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chat-messages.component.html',
  providers: [ChatMessagesService],
  host: {
    class: 'block'
  }
})
export class ChatMessagesComponent implements OnChanges, OnDestroy {
  constructor(private chatMessagesService: ChatMessagesService) {}

  @Input({ required: true }) chatId: string = '';

  addKey(chatId?: string): string {
    return `chat:${chatId || this.chatId}:messages`;
  }

  updateKey(chatId?: string): string {
    return `chat:${chatId || this.chatId}:messages:update`;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.handleSubscriptions(changes);
  }

  ngOnDestroy(): void {
    this.unsubscribeEvents(this.chatId);
  }

  private handleSubscriptions(changes: SimpleChanges): void {
    const previousValue = changes['chatId'].previousValue;
    previousValue && this.unsubscribeEvents(previousValue);
    this.chatMessagesService.subscribeMessages(this.addKey());
  }

  private unsubscribeEvents(key: string): void {
    this.chatMessagesService.unsubscribe(this.addKey(key), this.updateKey(key));
  }
}
