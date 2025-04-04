import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { i18nSupported } from './shared/constants/i18n-supported.constant';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(private translateService: TranslateService) {
    this.translateService.use(i18nSupported.EN);
  }
}
