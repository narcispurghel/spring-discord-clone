import { Routes } from '@angular/router';
import { ChannelsComponent } from './channels.component';

export const CHANNELS_ROUTES: Routes = [
  {
    path: ':channelId',
    component: ChannelsComponent
  }
];
