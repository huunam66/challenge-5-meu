import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ToastrService } from 'ngx-toastr';
import { catchError, tap, throwError } from 'rxjs';
import { Profile } from '../../../../../../../model/profile.model';
import { ResponseResult } from '../../../../../../../model/responseResult.model';
import { ProfileService } from '../../../../../../../service/profile.service';

@Component({
  selector: 'app-information',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    MatIconModule,
    DatePipe,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule
  ],
  templateUrl: './information.component.html',
  styleUrl: './information.component.scss'
})
export class InformationComponent {

  @Input("profile") IProfile!: Profile;
  @Output() onReGetProfile = new EventEmitter();

  informationReadySave: FormGroup = new FormGroup({
    id: new FormControl(''),
    first_name: new FormControl(''),
    last_name: new FormControl(''),
    identification_code: new FormControl(''),
    birthDay: new FormControl(new Date()),
    gender: new FormControl(''),
    avatar: new FormControl(''),
    email: new FormControl('')
  });

  isEditProfileInformation: boolean = false;

  constructor(
    private toastr: ToastrService,
    private profileService: ProfileService
  ) { }


  onBirthDayDateChange(event: any) {
    console.log(event)
  }

  onIconEditProfileInformationClick() {
    this.informationReadySave.patchValue({
      id: this.IProfile.id || '',
      first_name: this.IProfile.first_name || '',
      last_name: this.IProfile.last_name || '',
      identification_code: this.IProfile.identification_code || '',
      birthDay: this.IProfile.birthDay || new Date(),
      gender: this.IProfile.gender || '',
      avatar: this.IProfile.avatar || '',
      email: this.IProfile.email || ''
    });

    this.isEditProfileInformation = true;
  }

  onIconCloseEditProfileInformationClick() {
    this.isEditProfileInformation = false;
  }

  onSaveProfileInformationClick() {

    const profileInformationToSave: Profile = this.informationReadySave.value;

    this.profileService.postProfile(profileInformationToSave)
      .pipe(
        tap((res: ResponseResult<Profile>) => {
          this.toastr.success(res.message, '')
          this.onReGetProfile.emit();
          this.isEditProfileInformation = false;
        }),

        catchError((error: ResponseResult<never>) => {
          this.toastr.error(error.message, '');
          return throwError(() => error);
        })
      )
      .subscribe()
  }
}
