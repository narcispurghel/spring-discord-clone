import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, catchError, throwError } from 'rxjs';
import { AppState } from 'src/app/store/app.state';
import { LogoutSuccessAction } from '../../authentication/store/auth.actions';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store<AppState>) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error) => {
        error instanceof HttpErrorResponse &&
          error.status === 401 &&
          this.store.dispatch(LogoutSuccessAction({}));

        return throwError(() => error);
      })
    );
  }
}
