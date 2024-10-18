import { Profile } from './../../../../../../model/user/profile.model';

import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { District } from '../../../../../../model/location/district.model';
import { Province } from '../../../../../../model/location/province.model';
import { Ward } from '../../../../../../model/location/ward.model';
import { LocationApiService } from '../../../../../../service/api/location-api.service';
import { ProfileApiService } from '../../../../../../service/api/profile-api.service';

@Component({
  selector: 'app-location',
  standalone: true,
  imports: [MatIconModule, FormsModule, CommonModule],
  templateUrl: './location.component.html',
  styleUrl: './location.component.scss'
})
export class LocationComponent implements OnInit, OnDestroy {

  @Input("profile")
  profile!: Profile;

  @Output("onReGetProfile")
  onReGetProfile = new EventEmitter();

  isEditProfileLocation: boolean = false;

  provinces: Province[] = [];

  districts: District[] = [];

  wards: Ward[] = [];

  private subscription: Subscription;

  constructor(
    private toastrService: ToastrService,
    private profileApiService: ProfileApiService,
    private locationApiService: LocationApiService
  ){}

  ngOnInit(): void {
    this.onLoadLocationProfile();
  }

  ngOnDestroy(): void {
      this.subscription?.unsubscribe();
  }


  onSaveProfileLocationClick(){
    this.subscription = this.profileApiService.postProfileLocation(this.profile)
                .pipe(
                  finalize(() => this.subscription.unsubscribe())
                )
                .subscribe({
                  next: (res) => {
                    // console.log(res);
                    this.toastrService.success(res.message, '')
                    this.onReGetProfile.emit();
                    this.isEditProfileLocation = false;
                  },
                  error: (err) => {
                    console.log(err);
                  }
                })
  }

  onIconEditProfileLocationClick(){
    this.isEditProfileLocation = true;
  }

  onIconCloseEditProfileLocationClick(){
    this.isEditProfileLocation = false;
  }


  onLoadWardsByDistrictId(districtId: string){

    this.subscription = this.locationApiService.getAllWardsByDistrictId(districtId)
                    .pipe(
                      finalize(() => this.subscription.unsubscribe())
                    )
                    .subscribe({
                      next: (res) => {
                        // console.log(res);
                        this.wards = res.data.wards;
                        // console.log(this.wards)
                      },
                      error: (err) => {
                        console.log(err);
                      }
                    })

  }


  onLoadProvinces(){
    this.subscription = this.locationApiService.getAllProvinces()
                    .pipe(
                      finalize(() => this.subscription.unsubscribe())
                    )
                    .subscribe({
                      next: (res) => {
                        // console.log(res)
                        this.provinces = res.data.provinces;
                        // console.log(this.provinces);
                      },
                      error: (err) => {
                        console.log(err);
                      }
                    })
  }


  onLoadDistrictsByProvinceId(provinceId: string){
    this.subscription = this.locationApiService.getAllDistrictsByProvinceId(provinceId)
        .pipe(
          finalize(() => this.subscription.unsubscribe())
        )
        .subscribe({
          next: (res) => {
            // console.log(res);
            this.districts = res.data.districts;
            this.wards = [];
          },
          error: (err) => {
            console.log(err);
          }
        })
  }

  onLoadLocationProfile(){
    this.onLoadProvinces();

    const provinceId: string = this?.profile?.profileLocation?.ward?.district?.province?.id;

    this.onLoadDistrictsByProvinceId(provinceId)

    const districtId: string = this?.profile?.profileLocation?.ward?.district?.id;

    this.onLoadWardsByDistrictId(districtId);


    console.log(this.provinces)
    console.log(this.districts)
    console.log(this.wards)
  }

  onChangeProvinceCombobox(e: any){
    const provinceId: string = e.target.value;

    if(!provinceId) {
      this.districts = [];
      this.wards = [];
      return;
    }

    this.onLoadDistrictsByProvinceId(provinceId);
  }

  onChangeDistrictCombobox(e: any){
    const districtId: string = e.target.value;

    if(!districtId){
      this.wards = [];
      return;
    }

    this.onLoadWardsByDistrictId(districtId);
  }

}
