import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { ProfileApiService } from '../../../../../../../service/profile/profile.service';
import { Profile } from '../../../../../../model/profile.model';

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
    MatNativeDateModule
  ],
  templateUrl: './information.component.html',
  styleUrl: './information.component.scss'
})
export class InformationComponent {


  @Input("profile")
  profile!: Profile;

  @Output("onReGetProfile")
  onReGetProfile = new EventEmitter();

  isEditProfileInformation: boolean = false;

  private subscription: Subscription;

  constructor(
    private toastrService: ToastrService,
    private profileApiService: ProfileApiService
  ) { }


  onBirthDayDateChange(event: any) {
    console.log(event)
  }

  onIconEditProfileInformationClick() {
    this.isEditProfileInformation = true;
  }

  onIconCloseEditProfileInformationClick() {
    this.isEditProfileInformation = false;
  }

  onSaveProfileInformationClick() {
    this.subscription = this.profileApiService.postProfile(this.profile)
      .pipe(
        finalize(() => this.subscription.unsubscribe())
      )
      .subscribe({
        next: (res) => {
          // console.log(res);
          this.toastrService.success(res.message, '')
          this.onReGetProfile.emit();
          this.isEditProfileInformation = false;
        },
        error: (err) => {
          console.log(err);
        }
      })
  }
}
