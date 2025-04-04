import { AfterContentInit, Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[appAutofocus]',
  standalone: true
})
export class AutoFocusDirective implements AfterContentInit {
  constructor(private elRef: ElementRef<HTMLElement>) {}

  @Input() set shouldFocus(focus: boolean) {
    focus && this.focus();
  }

  ngAfterContentInit(): void {
    this.focus();
  }

  private focus(): void {
    setTimeout(() => {
      this.elRef.nativeElement.focus();
    });
  }
}
