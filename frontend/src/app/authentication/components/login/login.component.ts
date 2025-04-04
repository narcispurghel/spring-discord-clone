import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
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
import {
  EMAIL_CHECKER_REGEXP,
  PASSWORD_MIN_LENGTH
} from '../../constants/auth.constants';
import { AuthFormErrorsHandler } from '../../services/auth-form-errors-handler.service';
import { AuthFacade } from '../../store/auth.facade';
import { AuthFooterLinkComponent } from '../auth-footer-link/auth-footer-link.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    TranslateModule,
    MatFormFieldModule,
    MatInputModule,
    ButtonDirective,
    ReactiveFormsModule,
    RouterModule,

    AuthFooterLinkComponent,
    FocusInvalidFieldDirective
  ],
  providers: [AuthFormErrorsHandler],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  constructor(
    private authFacade: AuthFacade,
    private fb: FormBuilder,
    private formErrorsHandler: AuthFormErrorsHandler
  ) {}

  formGroup!: FormGroup;

  isLoading$ = this.authFacade.getIsLoading$('login');

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit(): void {
    this.formGroup.markAllAsTouched();

    if (this.formGroup.valid) {
      const rawValue = this.formGroup.getRawValue();
      this.authFacade.login({
        username: rawValue.email,
        password: rawValue.password
      });
    }
  }

  private initForm(): void {
    this.formGroup = this.fb.group({
      email: new FormControl('', [
        Validators.required,
        Validators.pattern(EMAIL_CHECKER_REGEXP)
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(PASSWORD_MIN_LENGTH)
      ])
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
