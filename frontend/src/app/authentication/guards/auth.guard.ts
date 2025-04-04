import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot
} from '@angular/router';
import { map } from 'rxjs';
import { UserService } from '../services/user.service';

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {
  const router = inject(Router);

  const user = inject(UserService).getUser$();
  const navigatesToAuth = route.routeConfig?.path?.includes('authentication');

  return user.pipe(
    map((user) => {
      if (navigatesToAuth) {
        user && router.navigate(['/']);
        return user ? false : true;
      } else {
        !user &&
          router.navigate(['/authentication'], {
            queryParams: route.queryParams
          });
        return !!user;
      }
    })
  );
};
