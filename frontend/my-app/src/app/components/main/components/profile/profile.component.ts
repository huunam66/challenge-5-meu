import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { finalize, Subscription } from 'rxjs';
import { PayloadToken } from '../../../../model/common/payload-token.model';
import { ProfileLocation } from '../../../../model/user/profile-location.model';
import { Profile } from '../../../../model/user/profile.model';
import { ProfileApiService } from './../../../../service/api/profile-api.service';
import { JwtService } from './../../../../service/utils/jwt.service';
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
export class ProfileComponent implements OnInit, OnDestroy{

  profile: Profile = new Profile();

  private subscription: Subscription;
  constructor(
    private profileApiService: ProfileApiService,
    private jwtService: JwtService
  ){}

  ngOnInit(): void {

    this.onGetProfile();
  }

  ngOnDestroy(): void {
      this.subscription?.unsubscribe();
  }

  onGetProfile(){
    // console.log("hello")

    const payload: PayloadToken = this.jwtService.getPayload();

    this.subscription = this.profileApiService.getProfile(payload?.sub)
    .pipe(
      finalize(() => this.subscription.unsubscribe())
    )
    .subscribe({
      next: (res: any) => {
        console.log(res);
        this.profile = res.data.profile;
        if(!this.profile.profileLocation) this.profile.profileLocation = new ProfileLocation();
        // console.log(this.profile);
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
