import { CommonModule } from '@angular/common';
import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  inject
} from '@angular/core';
import { MatDividerModule } from '@angular/material/divider';
import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { MemberRole } from 'src/app/app-shell/interfaces/member.interface';
import { UserDef } from 'src/app/authentication/interfaces/auth.interface';
import {
  ServerSearchAndSectionData,
  ServerWithChannelsMembersAndProfilesDef
} from '../../interfaces/servers.interfaces';
import { ServerSidebarService } from '../../services/server-sidebar.service';
import { ServerChannelComponent } from './server-channel/server-channel.component';
import { ServerHeaderComponent } from './server-header/server-header.component';
import { ServerMemberComponent } from './server-member/server-member.component';
import { ServerSearchComponent } from './server-search/server-search.component';
import { ServerSectionComponent } from './server-section/server-section.component';

@Component({
  selector: 'app-server-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    ServerHeaderComponent,
    ServerSearchComponent,
    ServerSectionComponent,
    MatDividerModule,
    ServerChannelComponent,
    ServerMemberComponent
  ],
  templateUrl: './server-sidebar.component.html',
  providers: [ServerSidebarService]
})
export class ServerSidebarComponent implements OnInit, OnChanges {
  @Input({ required: true }) server!: ServerWithChannelsMembersAndProfilesDef;
  @Input({ required: true }) user!: UserDef;
  private serversSidebarService = inject(ServerSidebarService);

  channelTypes = ChannelType;
  data!: ServerSearchAndSectionData;
  role!: MemberRole;

  ngOnInit(): void {
    this.mapToData();
  }

  ngOnChanges(changes: SimpleChanges): void {
    changes['server'] && !changes['server'].firstChange && this.mapToData();
  }

  private mapToData(): void {
    this.role = this.server.members.find(
      (member) => member.profileId === this.user.id
    )?.role as MemberRole;
    this.data = this.serversSidebarService.getSearchData(
      this.user.id,
      this.server
    );
  }
}
