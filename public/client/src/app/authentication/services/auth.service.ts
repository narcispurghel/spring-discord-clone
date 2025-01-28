import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, take } from 'rxjs';
import { API_URL_CONSTANTS } from 'src/app/shared/constants/api-urls.constants';
import {
  LoginFields,
  RegisterFields,
  UpdateProfileDto,
  UserDef,
  UserDto
} from '../interfaces/auth.interface';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  register$(registerFields: RegisterFields): Observable<UserDef> {
    return this.http.post<UserDto>(
      API_URL_CONSTANTS.AUTH.REGISTER,
      registerFields
    );
  }

  login$(loginFields: LoginFields): Observable<UserDef> {
    return this.http.post<UserDto>(API_URL_CONSTANTS.AUTH.LOGIN, loginFields);
  }

  logout$(): Observable<any> {
    return this.http.get(API_URL_CONSTANTS.AUTH.LOGOUT);
  }

  updateProfile$(data: UpdateProfileDto): Observable<UserDef> {
    return this.http.patch<UserDto>(
      API_URL_CONSTANTS.USER.UPDATE_PROFILE,
      data
    );
  }

  handleRoutingOnAuthCompleted(): void {
    this.route.queryParams
      .pipe(take(1))
      .subscribe(({ inviteCode }) =>
        inviteCode
          ? this.router.navigate(['/invite'], { queryParams: { inviteCode } })
          : this.router.navigate(['/'])
      );
  }
}
