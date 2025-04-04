import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonDirective } from 'src/app/shared/directives/button.directive';
import { FocusInvalidFieldDirective } from 'src/app/shared/directives/focus-invalid-field.directive';
import { passwordMatchesValidator } from 'src/app/shared/validators/forms/password-matches';
import {
  EMAIL_CHECKER_REGEXP,
  PASSWORD_MIN_LENGTH
} from '../../constants/auth.constants';
import { AuthFormErrorsHandler } from '../../services/auth-form-errors-handler.service';
import { AuthFacade } from '../../store/auth.facade';
import { AuthFooterLinkComponent } from '../auth-footer-link/auth-footer-link.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    TranslateModule,
    MatFormFieldModule,
    MatInputModule,
    ButtonDirective,
    ReactiveFormsModule,

    AuthFooterLinkComponent,
    FocusInvalidFieldDirective
  ],
  providers: [AuthFormErrorsHandler],
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private formErrorsHandler: AuthFormErrorsHandler,
    private authFacade: AuthFacade
  ) {}

  formGroup!: FormGroup;

  isLoading$ = this.authFacade.getIsLoading$('register');

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit(): void {
    this.formGroup.markAllAsTouched();

    if (this.formGroup.valid) {
      const rawForm = this.formGroup.getRawValue();
      this.authFacade.register({
        email: rawForm.email,
        name: rawForm.name,
        password: rawForm.passwords.password
      });
    }
  }

  private initForm(): void {
    this.formGroup = this.fb.group({
      name: new FormControl('', [Validators.required]),
      email: new FormControl('', [
        Validators.required,
        Validators.pattern(EMAIL_CHECKER_REGEXP)
      ]),
      passwords: this.fb.group(
        {
          password: new FormControl('', [
            Validators.required,
            Validators.minLength(PASSWORD_MIN_LENGTH)
          ]),
          confirmPassword: new FormControl('', [
            Validators.required,
            Validators.minLength(PASSWORD_MIN_LENGTH)
          ])
        },
        { validators: passwordMatchesValidator }
      )
    });
  }

  mapError = (controlKey: string): string => {
    const control = this.getControl(controlKey);
    return control?.invalid
      ? this.formErrorsHandler.getErrorTranslation(control)
      : '';
  };

  hasError(controlKey: string): boolean {
    const control = this.getControl(controlKey);
    return control
      ? control.invalid && (control.touched || control.dirty)
      : false;
  }

  private getControl = (controlKey: string): FormControl | null =>
    this.formGroup.get(controlKey) as FormControl;
}
