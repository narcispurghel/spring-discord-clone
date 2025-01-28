import {
  ChannelDef,
  ChannelType
} from 'src/app/app-shell/interfaces/channel.interface';

export interface ChannelModalData {
  channel?: ChannelDef;
  defaultChannelType?: ChannelType;
}
