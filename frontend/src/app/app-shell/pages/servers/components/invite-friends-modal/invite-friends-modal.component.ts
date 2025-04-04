import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';
import { takeWhile } from 'rxjs';
import { CopyToClipboardComponent } from 'src/app/shared/components/copy-to-clipboard/copy-to-clipboard.component';
import { ModalBodyComponent } from 'src/app/shared/components/modal/modal-body/modal-body.component';
import { ModalFooterComponent } from 'src/app/shared/components/modal/modal-footer/modal-footer.component';
import { ModalTitleComponent } from 'src/app/shared/components/modal/modal-title/modal-title.component';
import { ModalComponent } from 'src/app/shared/components/modal/modal.component';
import { OriginService } from 'src/app/shared/services/origin.service';
import { ServerWithChannelsMembersAndProfilesDef } from '../../interfaces/servers.interfaces';
import { ServersService } from '../../services/servers.service';
import { ServersFacade } from '../../store/servers.facade';

@Component({
  selector: 'app-invite-friends-modal',
  standalone: true,
  imports: [
    CommonModule,
    TranslateModule,
    ModalComponent,
    ModalTitleComponent,
    ModalBodyComponent,
    ModalFooterComponent,
    ReactiveFormsModule,
    CopyToClipboardComponent,
    MatIconModule
  ],
  templateUrl: './invite-friends-modal.component.html',
  providers: [OriginService, ServersService]
})
export class InviteFriendsModalComponent implements OnInit, OnDestroy {
  private serversFacade = inject(ServersFacade);
  private serversService = inject(ServersService);
  private originService = inject(OriginService);

  isLoading$ = this.serversFacade.getIsLoading$('generate-new-invite-code');
  control = new FormControl({ value: '', disabled: true });
  currentServerData!: ServerWithChannelsMembersAndProfilesDef | null;

  private alive = true;

  inviteUrl(inviteCode: string): string {
    return `${this.originService.getOrigin()}/invite?inviteCode=${inviteCode}`;
  }

  ngOnInit(): void {
    this.subscribeCurrentServerData();
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  onCopy(): void {
    navigator.clipboard.writeText(
      this.inviteUrl(this.currentServerData?.inviteCode || '')
    );
  }

  generateNewLink(): void {
    this.serversFacade.generateNewInviteCode(this.currentServerData?.id || '');
  }

  private subscribeCurrentServerData(): void {
    this.serversFacade
      .currentServerData$()
      .pipe(takeWhile(() => this.alive))
      .subscribe((server) => {
        this.currentServerData = server;
        this.control.setValue(this.inviteUrl(server?.inviteCode || ''));
      });
  }
}
