import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileService } from './profile.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { profileInterceptor } from './profile.interceptor';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    ProfileService,
    {
      provide: HTTP_INTERCEPTORS,
      useValue: profileInterceptor,
      multi: true
    }
  ]
})
export class ProfileModule { }
