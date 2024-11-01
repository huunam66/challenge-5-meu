import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, tap, throwError } from 'rxjs';
import { District, Province, Ward } from '../../../../../../../model/location.model';
import { ProfileLocation } from '../../../../../../../model/profile.model';
import { ResponseResult } from '../../../../../../../model/responseResult.model';
import { LocationService } from '../../../../../../../service/location.service';
import { ProfileService } from '../../../../../../../service/profile.service';

@Component({
  selector: 'app-location',
  standalone: true,
  imports: [MatIconModule, FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './location.component.html',
  styleUrl: './location.component.scss'
})
export class LocationComponent implements OnInit {

  @Input("profileLocation") IProfileLocation: ProfileLocation;
  @Output() onReGetProfile = new EventEmitter();

  profileLocationReadySave: FormGroup = new FormGroup({
    id: new FormControl(''),
    home_number: new FormControl(''),
    street: new FormControl(''),
    country: new FormControl(''),
    ward: new FormGroup({
      id: new FormControl('')
    })
  });

  provinces: Province[] = [];
  districts: District[] = [];
  wards: Ward[] = [];

  isEditProfileLocation: boolean = false;

  constructor(
    private toastrService: ToastrService,
    private profileService: ProfileService,
    private locationService: LocationService
  ) { }

  ngOnInit(): void {
    this.onLoadLocationProfile();
  }


  onIconEditProfileLocationClick() {
    this.profileLocationReadySave.patchValue({
      id: this.IProfileLocation.id || '',
      home_number: this.IProfileLocation.home_number || '',
      street: this.IProfileLocation.street || '',
      country: this.IProfileLocation.country || '',
    });

    this.profileLocationReadySave.get('ward')?.patchValue({
      id: this.IProfileLocation.ward?.id || ''
    })

    this.isEditProfileLocation = true;
  }

  onIconCloseEditProfileLocationClick() {
    this.isEditProfileLocation = false;
  }

  onSaveProfileLocationClick() {
    const profileLocationToSave: ProfileLocation = {
      ...this.profileLocationReadySave.value,
      ward: this.profileLocationReadySave.value.ward.value || undefined
    }

    this.profileService
      .postProfileLocation(profileLocationToSave)
      .pipe(
        tap((res: ResponseResult<ProfileLocation>) => {
          this.toastrService.success(res.message, '')
          this.onReGetProfile.emit();
          this.isEditProfileLocation = false;
        }),

        catchError((error: ResponseResult<never>) => {
          this.toastrService.error(error.message, '');
          return throwError(() => error);
        })
      )
      .subscribe()
  }

  onLoadWardsByDistrictId(districtId: string) {
    this.locationService
      .getAllWardsByDistrictId(districtId)
      .pipe(
        tap((res: ResponseResult<Ward[]>) => {
          this.wards = res.data || [];
        })
      )
      .subscribe();

  }


  onLoadProvinces() {
    this.locationService
      .getAllProvinces()
      .pipe(
        tap((res: ResponseResult<Province[]>) => {
          this.provinces = res.data || [];
        })
      )
      .subscribe();
  }


  onLoadDistrictsByProvinceId(provinceId: string) {
    this.locationService.
      getAllDistrictsByProvinceId(provinceId)
      .pipe(
        tap((res: ResponseResult<District[]>) => {
          this.districts = res.data || [];
        })
      )
      .subscribe();
  }

  onLoadLocationProfile() {
    this.onLoadProvinces();

    const { id: provinceId } = this.IProfileLocation.ward?.district?.province || {} as Province;

    this.onLoadDistrictsByProvinceId(provinceId || '')

    const { id: districtId } = this.IProfileLocation.ward?.district || {} as District;

    this.onLoadWardsByDistrictId(districtId || '');

  }

  onChangeProvinceCombobox(e: any) {
    const provinceId: string = e.target.value;

    if (!provinceId) {
      this.districts = [];
      this.wards = [];
      return;
    }

    this.onLoadDistrictsByProvinceId(provinceId);
  }

  onChangeDistrictCombobox(e: any) {
    const districtId: string = e.target.value;

    if (!districtId) {
      this.wards = [];
      return;
    }

    this.onLoadWardsByDistrictId(districtId);
  }

}
