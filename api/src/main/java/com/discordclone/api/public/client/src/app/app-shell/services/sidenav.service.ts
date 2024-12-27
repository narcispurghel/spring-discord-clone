import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SidenavService {
  isOpen$ = new BehaviorSubject(false);

  toggleSidenav = (): void => this.isOpen$.next(!this.isOpen$.value);

  closeIfOpen(): void {
    const sidenavOpen = this.isOpen$.value;
    sidenavOpen && this.isOpen$.next(!sidenavOpen);
  }

  close(): void {
    this.isOpen$.next(false);
  }
}
