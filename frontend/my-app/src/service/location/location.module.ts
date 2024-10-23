import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LocationService } from './location.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { locationInterceptor } from './location.interceptor';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    LocationService,
    {
      provide: HTTP_INTERCEPTORS,
      useValue: locationInterceptor,
      multi: true
    }
  ]
})
export class LocationModule { }
