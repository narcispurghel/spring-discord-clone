import { CommonModule } from '@angular/common';
import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { PickerComponent } from '@ctrl/ngx-emoji-mart';
import { TranslateModule } from '@ngx-translate/core';
import { OnSubmitChatInputData } from 'src/app/shared/interfaces/chat-input.interface';
import { EmojiEventDef } from 'src/app/shared/interfaces/emojies.interface';

@Component({
  selector: 'app-chat-input',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    FormsModule,
    PickerComponent,
    MatMenuModule,
    TranslateModule
  ],
  templateUrl: './chat-input.component.html'
})
export class ChatInputComponent implements OnInit, AfterViewInit {
  @Input({ required: true }) name: string = '';
  @Input({ required: true }) type: 'conversation' | 'channel' = 'channel';
  @Output() onSubmit = new EventEmitter<OnSubmitChatInputData>();

  inputValue = '';

  ngOnInit(): void {}

  ngAfterViewInit(): void {}

  onSendMessage(): void {
    const inputValue = this.inputValue.trim();
    if (inputValue) {
      this.onSubmit.emit({ content: this.inputValue });
      this.inputValue = '';
    }
  }

  addEmoji(event: EmojiEventDef): void {
    this.inputValue += event.emoji.native;
  }
}
