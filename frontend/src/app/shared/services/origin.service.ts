import { Injectable } from '@angular/core';

@Injectable()
export class OriginService {
  getOrigin(): string {
    return typeof window !== 'undefined'
      ? window.location.origin
        ? window.location.origin
        : ''
      : '';
  }
}
