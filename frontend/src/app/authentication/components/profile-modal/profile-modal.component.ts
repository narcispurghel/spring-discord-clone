import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { TranslateModule } from '@ngx-translate/core';
import { UploadResponse } from 'imagekit-javascript/dist/src/interfaces';
import { skip, take } from 'rxjs';
import { ImageUploadComponent } from 'src/app/shared/components/inputs/image-upload/image-upload.component';
import { ModalBodyComponent } from 'src/app/shared/components/modal/modal-body/modal-body.component';
import { ModalFooterComponent } from 'src/app/shared/components/modal/modal-footer/modal-footer.component';
import { ModalSubtitleComponent } from 'src/app/shared/components/modal/modal-subtitle/modal-subtitle.component';
import { ModalTitleComponent } from 'src/app/shared/components/modal/modal-title/modal-title.component';
import { ModalComponent } from 'src/app/shared/components/modal/modal.component';
import { ButtonDirective } from 'src/app/shared/directives/button.directive';
import { ImageKitFolders } from 'src/app/shared/lib/image-kit/constants/image-kit.constants';
import { FormValidationService } from 'src/app/shared/services/form-validation.service';
import { AuthFacade } from '../../store/auth.facade';

@Component({
  selector: 'app-profile-modal',
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
  templateUrl: './profile-modal.component.html'
})
export class ProfileModalComponent {
  constructor(
    private authFacade: AuthFacade,
    private dialogRef: MatDialogRef<ProfileModalComponent>,
    private fb: FormBuilder,
    private formValidationService: FormValidationService
  ) {}

  form!: FormGroup;
  iKitFolders = ImageKitFolders;
  isLoading$ = this.authFacade.getIsLoading$('update-profile');

  get profileImageValue(): string {
    return this.form.get('profileImage')?.value || '';
  }

  ngOnInit(): void {
    this.initForm();
    this.closeDialogOnUserChange();
    this.patchForm();
  }

  onServerImageUploaded(event: UploadResponse | null): void {
    this.form.patchValue({ profileImage: event?.url || '' });
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    this.form.valid && this.authFacade.updateProfile(this.form.getRawValue());
  }

  hasError(controlName: string): boolean {
    return this.formValidationService.isInvalid(this.form, controlName);
  }

  private initForm(): void {
    this.form = this.fb.group({
      profileImage: new FormControl(''),
      username: new FormControl('', Validators.required)
    });
  }

  private closeDialogOnUserChange(): void {
    this.authFacade
      .getUser$()
      .pipe(skip(1), take(1))
      .subscribe(() => this.dialogRef.close());
  }

  private patchForm(): void {
    this.authFacade
      .getUser$()
      .pipe(take(1))
      .subscribe((user) =>
        this.form.patchValue({
          profileImage: user?.imageUrl,
          username: user?.name
        })
      );
  }
}
