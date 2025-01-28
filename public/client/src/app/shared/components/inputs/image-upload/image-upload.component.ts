import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

import { MatIconModule } from '@angular/material/icon';
import { TranslateModule } from '@ngx-translate/core';
import { UploadResponse } from 'imagekit-javascript/dist/src/interfaces';
import { BehaviorSubject, Observable } from 'rxjs';
import { ImageKitFolders } from 'src/app/shared/lib/image-kit/constants/image-kit.constants';
import { ImageKitService } from 'src/app/shared/lib/image-kit/image-kit.service';
import { LoadingSpinnerComponent } from '../../loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-image-upload',
  standalone: true,
  imports: [
    CommonModule,
    TranslateModule,
    LoadingSpinnerComponent,
    MatIconModule
  ],
  templateUrl: './image-upload.component.html',
  styleUrl: './image-upload.component.scss',
  providers: [ImageKitService]
})
export class ImageUploadComponent {
  constructor(private imageKitService: ImageKitService) {}

  @Input() value: string = '';
  @Input({ required: true }) folder!: ImageKitFolders;
  @Output() onUploaded = new EventEmitter<UploadResponse | null>();

  isLoading$ = new BehaviorSubject(false);

  onUpload(event: Event): void {
    const inputTarget = event.target as HTMLInputElement;
    const image = inputTarget.files![0];

    this.isLoading$.next(true);
    this.imageKitService
      .uploadImage$({ image, folder: this.folder })
      .subscribe({
        next: (data) => this.handleUpload(data),
        error: () => this.isLoading$.next(false)
      });
  }

  private handleUpload(data: Observable<UploadResponse>): void {
    data.subscribe({
      next: (uploadResponse) => {
        this.isLoading$.next(false);
        this.onUploaded.emit(uploadResponse);
      },
      error: () => this.isLoading$.next(false)
    });
  }
}
