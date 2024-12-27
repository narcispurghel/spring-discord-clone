import { CommonModule } from '@angular/common';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { TranslateModule } from '@ngx-translate/core';
import { UploadResponse } from 'imagekit-javascript/dist/src/interfaces';
import { skip, takeWhile } from 'rxjs';
import { ButtonDirective } from 'src/app/shared/directives/button.directive';
import { ImageKitFolders } from 'src/app/shared/lib/image-kit/constants/image-kit.constants';
import { FormValidationService } from 'src/app/shared/services/form-validation.service';
import { ImageUploadComponent } from '../../../shared/components/inputs/image-upload/image-upload.component';
import { ModalBodyComponent } from '../../../shared/components/modal/modal-body/modal-body.component';
import { ModalFooterComponent } from '../../../shared/components/modal/modal-footer/modal-footer.component';
import { ModalSubtitleComponent } from '../../../shared/components/modal/modal-subtitle/modal-subtitle.component';
import { ModalTitleComponent } from '../../../shared/components/modal/modal-title/modal-title.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { CreateEditServerModalData } from '../../interfaces/create-edit-server.interface';
import { ServerDef } from '../../interfaces/server.interface';
import { AppShellFacade } from '../../store/app-shell.facade';

@Component({
  selector: 'app-server-modal',
  standalone: true,
  imports: [
    CommonModule,
    ImageUploadComponent,
    MatInputModule,
    ReactiveFormsModule,
    ModalBodyComponent,
    ModalComponent,
    ModalFooterComponent,
    ModalSubtitleComponent,
    ModalTitleComponent,
    TranslateModule,
    ButtonDirective
  ],
  templateUrl: './server-modal.component.html'
})
export class ServerModalComponent implements OnInit, OnDestroy {
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: CreateEditServerModalData,
    private shellFacade: AppShellFacade,
    private fb: FormBuilder,
    private formValidationService: FormValidationService,
    private dialogRef: MatDialogRef<ServerModalComponent>
  ) {}

  form!: FormGroup;
  server?: ServerDef = this.data?.server;
  iKitFolders = ImageKitFolders;
  isLoading$ = this.shellFacade.getIsLoading$('create-edit-server');

  private alive = true;

  get serverImageValue(): string {
    return this.form.get('serverImage')?.value || '';
  }

  ngOnInit(): void {
    this.initForm();
    this.server && this.patchForm();
    this.subscribeServers();
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  onServerImageUploaded(event: UploadResponse | null): void {
    this.form.patchValue({ serverImage: event?.url || '' });
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const data = this.form.getRawValue();
      this.server
        ? this.shellFacade.editServer(this.server.id, data)
        : this.shellFacade.createServer(data);
    }
  }

  hasError(controlName: string): boolean {
    return this.formValidationService.isInvalid(this.form, controlName);
  }

  private initForm(): void {
    this.form = this.fb.group({
      serverImage: new FormControl('', Validators.required),
      serverName: new FormControl('', Validators.required)
    });
  }

  private patchForm(): void {
    this.form.patchValue({
      serverName: this.server?.name,
      serverImage: this.server?.imageUrl
    });
  }

  private subscribeServers(): void {
    this.shellFacade
      .servers$()
      .pipe(
        takeWhile(() => this.alive),
        skip(1)
      )
      .subscribe(() => this.dialogRef.close());
  }
}
