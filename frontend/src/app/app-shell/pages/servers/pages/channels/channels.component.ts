import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { filter, map, switchMap, take, takeWhile } from 'rxjs';
import { OnSubmitChatInputData } from 'src/app/shared/interfaces/chat-input.interface';
import { ChatInputComponent } from '../../../../../shared/components/inputs/chat-input/chat-input.component';
import { ChatHeaderComponent } from '../../components/chat-header/chat-header.component';
import { ChatMessagesComponent } from '../../../../../shared/components/chat-messages/chat-messages.component';
import { ServersFacade } from '../../store/servers.facade';
import { ChannelsFacade } from './store/channels.facade';

@Component({
  selector: 'app-channels',
  standalone: true,
  imports: [
    CommonModule,
    ChatHeaderComponent,
    ChatInputComponent,
    ChatMessagesComponent
  ],
  templateUrl: './channels.component.html'
})
export class ChannelsComponent implements OnInit, OnDestroy {
  private serversFacade = inject(ServersFacade);
  private route = inject(ActivatedRoute);
  private channelsFacade = inject(ChannelsFacade);

  channel$ = this.serversFacade.getActiveChannelId$().pipe(
    switchMap((channelId) =>
      this.serversFacade.getChannelFromCurrentServer$(channelId).pipe(
        take(1),
        filter((channel) => !!channel)
      )
    )
  );

  serverId$ = this.serversFacade
    .currentServerData$()
    .pipe(map((server) => server?.id));

  private alive = true;

  ngOnInit(): void {
    this.subscribeChannelId();
  }

  ngOnDestroy(): void {
    this.serversFacade.setActiveChannelId('');
    this.alive = false;
  }

  onSendMessage(event: OnSubmitChatInputData): void {
    this.channelsFacade.sendMessage(event);
  }

  private subscribeChannelId(): void {
    this.route.paramMap
      .pipe(takeWhile(() => this.alive))
      .subscribe((params) =>
        this.serversFacade.setActiveChannelId(params.get('channelId') || '')
      );
  }
}
