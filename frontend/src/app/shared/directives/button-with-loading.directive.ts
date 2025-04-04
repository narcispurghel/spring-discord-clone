import {
  AfterContentChecked,
  AfterContentInit,
  AfterViewInit,
  Directive,
  ElementRef,
  Input,
  OnInit,
  Renderer2
} from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Directive({
  selector: 'button[isLoading]',
  standalone: true
})
export class ButtonWithLoadingDirective implements AfterViewInit {
  constructor(
    private elRef: ElementRef<HTMLButtonElement>,
    private translateService: TranslateService,
    private renderer: Renderer2
  ) {
    this.buttonType = this.elRef.nativeElement.type;
  }

  ngAfterViewInit(): void {
    this.buttonInnerHTML = this.elRef.nativeElement.innerHTML;
  }

  private buttonInnerHTML: string = '';
  private readonly isLoadingText = `${this.translateService.instant(
    'content.loading'
  )}...`;

  private buttonType: 'submit' | 'reset' | 'button' = 'button';

  @Input('isLoading') set showSpinner(isLoading: boolean | null) {
    if (this.buttonInnerHTML) {
      if (isLoading) {
        this.elRef.nativeElement.type = 'button';
        this.renderer.addClass(this.elRef.nativeElement, 'opacity-70');
        this.renderer.addClass(this.elRef.nativeElement, 'pointer-events-none');
        this.elRef.nativeElement.innerHTML = this.isLoadingText;
      } else {
        this.elRef.nativeElement.type = this.buttonType;
        this.renderer.removeClass(this.elRef.nativeElement, 'opacity-70');
        this.renderer.removeClass(this.elRef.nativeElement, 'pointer-events-none');
        this.elRef.nativeElement.innerHTML = this.buttonInnerHTML;
      }
    }
  }
}
