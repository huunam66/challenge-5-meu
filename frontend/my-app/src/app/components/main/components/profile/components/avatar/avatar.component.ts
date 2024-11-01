import { Component, ElementRef, Input, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, tap, throwError } from 'rxjs';
import { PayloadToken } from '../../../../../../../model/payload-token.model';
import { AvatarResult, Profile } from '../../../../../../../model/profile.model';
import { ResponseResult } from '../../../../../../../model/responseResult.model';
import { ProfileService } from '../../../../../../../service/profile.service';
import { JwtService } from '../../../../../../../utils/jwt.service';

@Component({
  selector: 'app-avatar',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './avatar.component.html',
  styleUrl: './avatar.component.scss'
})
export class AvatarComponent {
  private payload: PayloadToken;

  @ViewChild('inputTypeFileTag') inputTypeFileTag!: ElementRef<HTMLInputElement>;
  @ViewChild('avatarImgTag') avatarImgTag!: ElementRef<HTMLImageElement>;

  @Input("profile") profile!: Profile;

  constructor(
    private toastr: ToastrService,
    private profileService: ProfileService,
    private jwtService: JwtService
  ) {
    this.payload = jwtService.getPayload();
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

    this.profileService.uploadFile(formData)
      .pipe(

        tap((res: ResponseResult<AvatarResult>) => {

          const avatarResult: AvatarResult = res.data || {} as AvatarResult;

          this.avatarImgTag.nativeElement.src = `data:image;base64,${avatarResult.avatar}`
          this.toastr.success(res.message, '');
        }),

        catchError((error: ResponseResult<never>) => {
          this.toastr.error(error.message, '');
          return throwError(() => error);
        })

      ).subscribe();
  }

}
