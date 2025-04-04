import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import {
  MatDrawer,
  MatSidenav,
  MatSidenavModule
} from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { takeWhile } from 'rxjs';
import { SidenavService } from '../../services/sidenav.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { SidenavContentComponent } from './sidenav-content/sidenav-content.component';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    NavbarComponent,
    MatSidenavModule,
    SidenavContentComponent
  ],
  templateUrl: './layout.component.html'
})
export class LayoutComponent implements AfterViewInit, OnDestroy {
  constructor(private sidenavService: SidenavService) {}

  @ViewChild(MatSidenav) matSidenav!: MatDrawer;

  private alive = true;

  ngAfterViewInit(): void {
    this.sidenavService.isOpen$
      .pipe(takeWhile(() => this.alive))
      .subscribe((open) =>
        open ? this.matSidenav.open() : this.matSidenav.close()
      );
  }

  ngOnDestroy(): void {
    this.alive = false;
  }

  onCloseSidenav(): void {
    this.sidenavService.close();
  }
}
