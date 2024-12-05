import { Directive, ElementRef, Input } from '@angular/core';
import { ButtonWithLoadingDirective } from './button-with-loading.directive';

const buttonVariants = {
  ghost: 'hover:!bg-stone-500 !bg-transparent !text-black hover:!text-white'
};

type ButtonVariantKeys = keyof typeof buttonVariants;

@Directive({
  selector: '[appButton]',
  standalone: true,
  hostDirectives: [
    {
      directive: ButtonWithLoadingDirective,
      inputs: ['isLoading']
    }
  ],
  host: {
    class:
      'bg-blue-500 text-white px-3 hover:opacity-70 cursor-pointer py-2 rounded-md'
  }
})
export class ButtonDirective {
  constructor(private elRef: ElementRef<HTMLButtonElement>) {}

  @Input('variant') set variant(variant: ButtonVariantKeys) {
    this.elRef.nativeElement.classList.add(
      ...buttonVariants[variant].split(' ')
    );
  }
}
