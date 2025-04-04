import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, take } from 'rxjs';
import { UserDef } from '../interfaces/auth.interface';
import { authUser } from '../store/auth.selectors';
import { AuthState } from '../store/auth.state';
import { AppState } from 'src/app/store/app.state';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private store: Store<AppState>) {}

  getUser$ = (): Observable<UserDef | null> => this.store.select(authUser);
}
