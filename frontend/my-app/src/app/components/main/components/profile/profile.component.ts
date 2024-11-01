import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, Subscription, tap, throwError } from 'rxjs';
import { PayloadToken } from '../../../../../model/payload-token.model';
import { Profile } from '../../../../../model/profile.model';
import { ResponseResult } from '../../../../../model/responseResult.model';
import { ProfileService } from '../../../../../service/profile.service';
import { JwtService } from '../../../../../utils/jwt.service';
import { AvatarComponent } from "./components/avatar/avatar.component";
import { InformationComponent } from "./components/information/information.component";
import { LocationComponent } from "./components/location/location.component";

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [MatIconModule, InformationComponent, LocationComponent, AvatarComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit, OnDestroy {

  IProfile: Profile;

  private subscription: Subscription;
  constructor(
    private profileService: ProfileService,
    private jwtService: JwtService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {

    this.onGetProfile();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  onGetProfile() {
    // console.log("hello")

    const payload: PayloadToken = this.jwtService.getPayload();

    this.profileService.getProfile(payload.sub || '')
      .pipe(
        tap((res: ResponseResult<Profile>) => {
          this.IProfile = res.data || {} as Profile;
        }),
        catchError((error: ResponseResult<never>) => {
          this.toastr.error(error.message, '');
          return throwError(() => error);
        })
      )
      .subscribe()
  }
}
