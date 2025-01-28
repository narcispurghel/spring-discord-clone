import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-copy-to-clipboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './copy-to-clipboard.component.html'
})
export class CopyToClipboardComponent {
  copied = signal(false);

  onCopy(): void {
    this.copied.set(true);
    setTimeout(() => this.copied.set(false), 1000);
  }
}
