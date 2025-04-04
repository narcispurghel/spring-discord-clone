import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SocketStatus {
  isConnected = signal(false);
}
