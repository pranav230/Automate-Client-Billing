import { Component, NgModule } from '@angular/core';
import { ServiceClassService } from './service-class.service';
import { ViewEncapsulation } from '@angular/core';

import { Router,NavigationEnd  } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AppComponent {

}

