import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, booleanAttribute } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

type LoadingSpinnerSize = 'small' | 'medium' | 'large';

const sizeMap: { [key in LoadingSpinnerSize]: number } = {
  large: 40,
  medium: 30,
  small: 20
};

@Component({
  selector: 'app-loading-spinner',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule],
  templateUrl: './loading-spinner.component.html'
})
export class LoadingSpinnerComponent implements OnInit {
  @Input() size: LoadingSpinnerSize = 'medium';

  @Input({ transform: booleanAttribute }) center = false;

  mappedDiameter!: number;
  private _center = false;

  ngOnInit(): void {
    this.mappedDiameter = sizeMap[this.size];
  }
}
