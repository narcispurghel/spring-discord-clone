import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { map, switchMap, takeWhile } from 'rxjs';
import { ChatHeaderComponent } from '../../components/chat-header/chat-header.component';
import { ChatInputComponent } from '../../../../../shared/components/inputs/chat-input/chat-input.component';
import { ChatMessagesComponent } from '../../../../../shared/components/chat-messages/chat-messages.component';
import { ServersFacade } from '../../store/servers.facade';

@Component({
  selector: 'app-conversations',
  standalone: true,
  imports: [
    CommonModule,
    ChatHeaderComponent,
    ChatInputComponent,
    ChatMessagesComponent
  ],
  templateUrl: './conversations.component.html'
})
export class ConversationsComponent implements OnInit, OnDestroy {
  private serversFacade = inject(ServersFacade);
  private route = inject(ActivatedRoute);

  private alive = true;

  serverId$ = this.serversFacade
    .currentServerData$()
    .pipe(map((server) => server?.id));

  otherMemberProfile$ = this.serversFacade
    .getActiveMemberConversationId$()
    .pipe(
      switchMap((memberId) =>
        this.serversFacade.getMemberWithProfileFromCurrentServer$(memberId)
      )
    );

  ngOnInit(): void {
    this.subscribeConversationId();
  }

  ngOnDestroy(): void {
    this.alive = false;
    this.serversFacade.setActiveMemberConversationId('');
  }

  private subscribeConversationId(): void {
    this.route.paramMap
      .pipe(takeWhile(() => this.alive))
      .subscribe((params) => {
        const conversationId = params.get('conversationId') || '';
        this.serversFacade.setActiveMemberConversationId(conversationId);
      });
  }
}
