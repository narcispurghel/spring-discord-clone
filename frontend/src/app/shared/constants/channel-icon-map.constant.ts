import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';

interface ChannelIconMap
  extends Record<ChannelType, { class?: string; iconType: string } | null> {}

export const CHANNEL_ICON_MAP: ChannelIconMap = {
  [ChannelType.TEXT]: {
    class: 'mdi-medium mr-2 text-zinc-500 dark:text-zinc-400',
    iconType: 'tag'
  },
  [ChannelType.AUDIO]: {
    class: 'mdi-medium mr-2 text-zinc-500 dark:text-zinc-400',
    iconType: 'mic'
  },
  [ChannelType.VIDEO]: {
    class: 'mdi-medium mr-2 text-zinc-500 dark:text-zinc-400',
    iconType: 'videocam'
  }
};
