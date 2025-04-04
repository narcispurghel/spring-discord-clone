import { UploadResponse } from 'node_modules/imagekit-javascript/dist/src/interfaces';

export interface ImageKitImageDef extends Pick<UploadResponse, 'fileId' | 'url'> {}

export interface ImageDto {
  createdAt: string;
  fileId: string;
  id: string;
  updatedAt: string;
  url: string;
}

export interface ImageDef {
  createdAt: string;
  fileId: string;
  id: string;
  updatedAt: string;
  url: string;
}
