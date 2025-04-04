import { ImageKitFolders } from '../constants/image-kit.constants';

export interface SignatureObjectDto {
  token: string;
  expire: number;
  signature: string;
}

export interface SignatureObjectDef {
  token: string;
  expire: number;
  signature: string;
}

export interface UploadImagePayload {
  image: File;
  folder: ImageKitFolders;
}
