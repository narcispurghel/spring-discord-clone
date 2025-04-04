import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { TranslateModule } from '@ngx-translate/core';
import { ChannelType } from 'src/app/app-shell/interfaces/channel.interface';
import { ModalBodyComponent } from 'src/app/shared/components/modal/modal-body/modal-body.component';
import { ModalFooterComponent } from 'src/app/shared/components/modal/modal-footer/modal-footer.component';
import { ModalTitleComponent } from 'src/app/shared/components/modal/modal-title/modal-title.component';
import { ModalComponent } from 'src/app/shared/components/modal/modal.component';
import { ButtonDirective } from 'src/app/shared/directives/button.directive';
import { FormValidationService } from 'src/app/shared/services/form-validation.service';
import { channelName } from 'src/app/shared/validators/forms/channel-name';
import { ChannelModalData } from '../../interfaces/channel-modal.interface';
import { ChannelFormErrorsHandler } from '../../services/channel-form-errors-mapper.service';
import { ServersFacade } from '../../store/servers.facade';

@Component({
  selector: 'app-channel-modal',
  standalone: true,
  imports: [
    CommonModule,
    ModalComponent,
    ModalTitleComponent,
    ModalBodyComponent,
    ModalFooterComponent,
    TranslateModule,
    MatFormFieldModule,
    ButtonDirective,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule
  ],
  templateUrl: './channel-modal.component.html',
  providers: [ServersFacade, ChannelFormErrorsHandler]
})
export class ChannelModalComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) private data: ChannelModalData,
    private fb: FormBuilder,
    private formValidationService: FormValidationService,
    private serversFacade: ServersFacade,
    private formErrorsHandler: ChannelFormErrorsHandler
  ) {}

  form!: FormGroup;
  channelTypes = ChannelType;
  isLoading$ = this.serversFacade.getIsLoading$('create-edit-channel');
  isEditMode = !!this.data?.channel;

  ngOnInit(): void {
    this.initForm();
    this.data?.channel && this.patchForm();
    !this.data?.channel &&
      this.data?.defaultChannelType &&
      this.setDefaultChannelType(this.data.defaultChannelType);
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.valid) {
      const data = this.form.getRawValue();
      this.isEditMode
        ? this.serversFacade.editChannel({
            channelId: this.data.channel!.id,
            ...data
          })
        : this.serversFacade.createChannel(data);
    }
  }

  hasError(controlName: string): boolean {
    return this.formValidationService.isInvalid(this.form, controlName);
  }

  mapError = (controlKey: string): string => {
    const control = this.getControl(controlKey);
    return control?.invalid
      ? this.formErrorsHandler.getErrorTranslation(control)
      : '';
  };

  private getControl = (controlKey: string): FormControl | null =>
    this.form.get(controlKey) as FormControl;

  private initForm(): void {
    this.form = this.fb.group({
      channelName: new FormControl('', [Validators.required, channelName()]),
      channelType: new FormControl(this.channelTypes.TEXT, Validators.required)
    });
  }

  private patchForm(): void {
    this.form.patchValue({
      channelName: this.data.channel?.name,
      channelType: this.data.channel?.type
    });
  }

  private setDefaultChannelType(channelType: ChannelType) {
    this.form.patchValue({ channelType });
  }
}
