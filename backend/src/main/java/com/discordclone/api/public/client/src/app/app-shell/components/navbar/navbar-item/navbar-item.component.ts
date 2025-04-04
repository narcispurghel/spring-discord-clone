import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component, Input, inject } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-item',
  standalone: true,
  imports: [CommonModule, MatTooltipModule, NgOptimizedImage],
  templateUrl: './navbar-item.component.html'
})
export class NavbarItemComponent {
  @Input({ required: true }) activeStoreId: string | null = '';
  @Input({ required: true }) id: string = '';
  @Input({ required: true }) name: string = '';
  @Input({ required: true }) imageUrl: string = '';

  private router = inject(Router);

  onClick(): void {
    this.router.navigate(['servers', this.id]);
  }
}
