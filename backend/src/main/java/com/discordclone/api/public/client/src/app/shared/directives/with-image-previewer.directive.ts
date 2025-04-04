import {
  ComponentRef,
  Directive,
  ElementRef,
  HostListener,
  Renderer2,
  ViewContainerRef
} from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { ImagePreviewerModalComponent } from '../components/modals/image-previewer-modal/image-previewer-modal.component';
import { ImagePreviewerModalDataDef } from '../interfaces/modals/image-previewer.interfaces';
import { ModalService } from '../services/modal.service';

/**
 * @description The image should have a parent with position relative in order to center the zoom-in icon on the center.
 */
@Directive({
  selector: 'img[appWithImagePreviewer]',
  standalone: true,
  host: {
    draggable: 'false'
  }
})
export class WithImagePreviewerDirective {
  constructor(
    private elRef: ElementRef<HTMLImageElement>,
    private modalService: ModalService,
    private renderer: Renderer2,
    private viewContainerRef: ViewContainerRef
  ) {}

  @HostListener('click')
  onOpenPreviewerDialog(): void {
    const imageSource = this.elRef.nativeElement.src;
    const dialogData: ImagePreviewerModalDataDef = {
      imageSource
    };
    this.modalService.openRegular(ImagePreviewerModalComponent, dialogData);
  }

  @HostListener('mouseenter') onHover(): void {
    this.renderer.addClass(this.elRef.nativeElement, 'opacity-70');
    this.renderer.appendChild(this.parentNode, this.iconInstance);
  }

  @HostListener('mouseleave') onLeave(): void {
    this.renderer.removeClass(this.elRef.nativeElement, 'opacity-70');
    this.renderer.removeChild(this.parentNode, this.iconInstance);
  }

  private _iconRef?: ComponentRef<MatIcon>;

  get iconRef(): ComponentRef<MatIcon> {
    !this._iconRef && (this.iconRef = this.getComponentRef());

    return this._iconRef as any;
  }

  set iconRef(icon: ComponentRef<MatIcon>) {
    this._iconRef = icon;
  }

  get parentNode(): HTMLElement {
    return this.renderer.parentNode(this.elRef.nativeElement);
  }

  get iconInstance(): HTMLElement {
    return this.iconRef.location.nativeElement;
  }

  private getComponentRef(): ComponentRef<MatIcon> {
    const iconRef = this.viewContainerRef.createComponent(MatIcon);
    const element = iconRef.instance._elementRef.nativeElement as HTMLElement;
    element.innerHTML = 'zoom_in';
    this.renderer.setAttribute(
      element,
      'class',
      'absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 mdi-medium mdi-2xl'
    );
    return iconRef;
  }
}
