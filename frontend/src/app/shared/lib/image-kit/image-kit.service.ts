import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from, map } from 'rxjs';

import ImageKit from 'imagekit-javascript';
import { environment } from '../../../../environments/environment';
import { API_URL_CONSTANTS } from '../../constants/api-urls.constants';
import { SnackBarService } from '../../services/snackbar.service';
import { ImageKitFolders } from './constants/image-kit.constants';
import {
  SignatureObjectDef,
  SignatureObjectDto,
  UploadImagePayload
} from './interfaces/image-kit.interfaces';

const imageKitInstance = new ImageKit({
  publicKey: environment.IMAGE_KIT_PUBLIC_KEY,
  urlEndpoint: environment.IMAGE_KIT_URL_ENDPOINT
});

@Injectable()
export class ImageKitService {
  constructor(
    private http: HttpClient,
    private snackbarService: SnackBarService
  ) {}

  uploadImage$({ image, folder }: UploadImagePayload) {
    return this.getClientSideToken().pipe(
      map((tokenData) => this.startUpload(image, folder, tokenData))
    );
  }

  private startUpload(
    image: File,
    folder: ImageKitFolders,
    tokenData: SignatureObjectDef
  ) {
    const fileName = `${new Date().toISOString()}${folder}`;
    return from(
      imageKitInstance
        .upload({
          file: image,
          fileName,
          token: tokenData.token,
          signature: tokenData.signature,
          expire: tokenData.expire,
          folder
        })
        .catch((error) => {
          this.snackbarService.openSnackBar(
            'content.uploading-the-image-has-failed',
            'warning'
          );
          throw error;
        })
    );
  }

  private getClientSideToken(): Observable<SignatureObjectDef> {
    return this.http.get<SignatureObjectDto>(
      API_URL_CONSTANTS.UPLOAD.CLIENT_SIDE_UPLOADING
    );
  }
}
