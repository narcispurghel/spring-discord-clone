interface EmojiObjectDef {
  id: string;
  name: string;
  colons: string;
  text: string;
  emoticons: string[];
  skin: number | null;
  native: string;
  custom?: boolean;
  imageUrl?: string;
}

export interface EmojiEventDef {
  $event: MouseEvent;
  emoji: EmojiObjectDef;
}
