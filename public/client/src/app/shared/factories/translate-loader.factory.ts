import { HttpBackend, HttpClient } from '@angular/common/http';
import { TranslateLoader } from '@ngx-translate/core';

export class CustomTranslateLoader implements TranslateLoader {
  private httpClient: HttpClient;
  constructor(private handler: HttpBackend) {
    this.httpClient = new HttpClient(handler);
  }

  getTranslation = (lang: string) =>
    this.httpClient.get(`./assets/locales/${lang}/default.json`);
}
