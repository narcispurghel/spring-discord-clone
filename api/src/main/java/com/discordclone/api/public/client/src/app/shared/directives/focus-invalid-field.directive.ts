import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: 'form[appFocusInvalidFormField]',
  standalone: true
})
export class FocusInvalidFieldDirective {
  constructor(private elementRef: ElementRef<HTMLFormElement>) {}

  @HostListener('submit', ['$event']) onSubmit(): void {
    const form = this.elementRef.nativeElement;
    const invalidControls = Array.from<HTMLInputElement>(
      form.querySelectorAll('input.ng-invalid')
    );
    invalidControls.length > 0 && invalidControls[0]?.focus();
  }
}
