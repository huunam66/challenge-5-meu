import { Component, ElementRef, Input, OnDestroy, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { ProfileApiService } from '../../../../../../../service/profile/profile.service';
import { JwtService } from '../../../../../../../utils/jwt.service';
import { PayloadToken } from '../../../../../../model/payload-token.model';
import { Profile } from '../../../../../../model/profile.model';

@Component({
  selector: 'app-avatar',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './avatar.component.html',
  styleUrl: './avatar.component.scss'
})
export class AvatarComponent implements OnDestroy {
  private payload!: PayloadToken;


  @ViewChild('inputTypeFileTag')
  inputTypeFileTag!: ElementRef<HTMLInputElement>;

  @ViewChild('avatarImgTag')
  avatarImgTag!: ElementRef<HTMLImageElement>;


  @Input("profile")
  profile!: Profile;

  private subscription: Subscription;

  constructor(
    private toastr: ToastrService,
    private profileApiService: ProfileApiService,
    private jwtService: JwtService
  ) {
    this.payload = jwtService.getPayload();
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  avatarClick() {
    this.inputTypeFileTag.nativeElement.click()
  }

  inputTypeFileOnChange(event: any) {
    const file = event.target.files[0];

    if (file.size > 60000) {
      const message: string = 'Kích thước file vượt quá 60 KB!';
      this.toastr.error(message, '');
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    this.subscription = this.profileApiService.uploadFile(formData)
      .pipe(
        finalize(() => {
          this.subscription.unsubscribe()
        })
      ).subscribe({
        next: (res) => {
          this.avatarImgTag.nativeElement.src = `data:image;base64,${res.data.avatar}`
          this.toastr.success(res.message, '');
        },
        error: (err) => {
          console.log(err)
          this.toastr.error(err.error.message, '');
        }
      });
  }

}
