import { CommonModule, NgOptimizedImage } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateModule } from '@ngx-translate/core';
import { ImagePreviewerModalDataDef } from 'src/app/shared/interfaces/modals/image-previewer.interfaces';
import { ModalBodyComponent } from '../../modal/modal-body/modal-body.component';
import { ModalFooterComponent } from '../../modal/modal-footer/modal-footer.component';
import { ModalSubtitleComponent } from '../../modal/modal-subtitle/modal-subtitle.component';
import { ModalTitleComponent } from '../../modal/modal-title/modal-title.component';
import { ModalComponent } from '../../modal/modal.component';

@Component({
  selector: 'app-image-previewer-modal',
  standalone: true,
  imports: [
    CommonModule,
    NgOptimizedImage,

    ModalBodyComponent,
    ModalComponent,
    ModalFooterComponent,
    ModalSubtitleComponent,
    ModalTitleComponent,
    TranslateModule
  ],
  templateUrl: './image-previewer-modal.component.html'
})
export class ImagePreviewerModalComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: ImagePreviewerModalDataDef
  ) {}
}
