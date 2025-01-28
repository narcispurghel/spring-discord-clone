import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { take } from 'rxjs';
import { LoadingSpinnerComponent } from 'src/app/shared/components/loading-spinner/loading-spinner.component';
import { AppShellFacade } from '../../store/app-shell.facade';

@Component({
  selector: 'app-invite',
  standalone: true,
  imports: [CommonModule, LoadingSpinnerComponent, TranslateModule],
  templateUrl: './invite.component.html'
})
export class InviteComponent implements OnInit {
  private shellFacade = inject(AppShellFacade);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  isLoading$ = this.shellFacade.getIsLoading$('join-server');

  ngOnInit(): void {
    this.route.queryParams
      .pipe(take(1))
      .subscribe(({ inviteCode }) =>
        inviteCode
          ? this.shellFacade.joinServer(inviteCode)
          : this.router.navigate(['/'])
      );
  }
}
