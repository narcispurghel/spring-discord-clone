import { CommonModule } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { take } from 'rxjs';

@Component({
  selector: 'app-auth-footer-link',
  standalone: true,
  imports: [RouterModule, CommonModule, TranslateModule],
  templateUrl: './auth-footer-link.component.html'
})
export class AuthFooterLinkComponent {
  @Input({ required: true }) authType: 'login' | 'register' = 'login';
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  navigate(): void {
    this.route.queryParams
      .pipe(take(1))
      .subscribe((queryParams) =>
        this.router.navigate(
          [
            this.authType === 'register'
              ? 'authentication/login'
              : 'authentication/register'
          ],
          { queryParams }
        )
      );
  }
}
