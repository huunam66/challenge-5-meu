import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, finalize, of, tap } from 'rxjs';
import { PayloadToken } from '../../../../../../model/payload-token.model';
import { ResponseResult } from '../../../../../../model/responseResult.model';
import { Grant, User } from '../../../../../../model/user.model';
import { AuthService } from '../../../../../../service/auth/auth.service';
import { UserService } from '../../../../../../service/user/user.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';
import { environment as environment_role } from './../../../../../../environments/environment.role-grant';

@Component({
  selector: 'app-detail-user',
  standalone: true,
  imports: [
    ConfirmBoxComponent,
    FormsModule,
    CommonModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.scss'
})
export class DetailUserComponent implements OnInit {

  @Input() emailDetail!: string;
  @Input() isDetailView!: boolean;
  @Input() isEditView!: boolean;
  @Input() isOpenThis: boolean;
  @Output() onCloseThis = new EventEmitter<any>();

  userDetail: FormGroup = new FormGroup({
    email: new FormControl(''),
    role: new FormControl('')
  });

  IUser: User;

  ROLE_GRANT: string[] = environment_role.ROLE_GRANT;
  currentRole!: string;
  isLoading: boolean;
  isConfirmExit: boolean = false;
  isConfirmSave: boolean = false;
  isConfirmDelete: boolean = false;
  payloadToken!: PayloadToken;

  constructor(
    private toastr: ToastrService,
    private userService: UserService,
    private authService: AuthService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }


  ngOnInit(): void {
    this.userService
      .getUserByEmail(this.emailDetail)
      .pipe(

        tap((res: ResponseResult<User>) => {
          this.userDetail.patchValue({
            email: res.data?.email,
            role: res.data?.role
          })

          this.IUser = { ...res.data };

          const { role } = this.userDetail.value;

          const isSuperAdmin: boolean = role === 'SUPER_ADMIN';
          const isAdmin: boolean = role === 'ADMIN';

          this.currentRole = isSuperAdmin ? "SUPER ADMIN" : isAdmin ? "ADMIN" : "USER";
        }),

        catchError((error) => {
          console.log(error);
          return of(null);
        })

      )
      .subscribe();
  }


  onTurnEditView(): void {
    this.isDetailView = false;
    this.isEditView = true;
  }

  backToDetailView(): void {
    this.isEditView = false;
    this.isDetailView = true;
  }

  onCancelConfirmSave(): void {
    this.isConfirmSave = false;
  }

  onCancelConfirmExit(): void {
    this.isConfirmExit = false;
  }

  onConfirmDelete(): void {
    this.isConfirmDelete = true;
  }

  onCancelConfirmDelete(): void {
    this.isConfirmDelete = false;
  }

  onDeleteProduct(): void {
    this.isLoading = true;

    setTimeout(() => {
      const { email } = this.IUser;

      this.userService
        .deleteUserByEmail(email!)
        .pipe(

          tap((res: ResponseResult<never>) => {
            this.isLoading = false;
            this.isConfirmDelete = false;

            this.onCloseThis.emit({
              statusClose: !this.isOpenThis,
              statusReloadTable: true,
            });
            this.toastr.success(res.message, '');
          }),

          catchError((error) => {
            console.log(error);
            return of(null);
          }),

          finalize(() => {
            this.isLoading = false;
            this.isConfirmSave = false;
          })

        )
        .subscribe();

    }, 2000);
  }

  onOkExitFormSave(): void {
    // console.log('saveProduct:');

    this.isOpenThis = !this.isOpenThis;

    // console.log(this.onCloseThis);

    setTimeout(() => {
      this.onCloseThis.emit({
        statusIsOpen: this.isOpenThis,
        statusReloadTable: false,
      });
    }, 400);
  }

  onCloseConfirmExit(event: boolean): void {
    this.isConfirmExit = event;
  }


  onSubmitAddNew(): void {

    // console.log(this.productModel);

    this.isConfirmSave = true;
  }

  onGrantAuthority(): void {
    this.isLoading = true;

    setTimeout(() => {
      const { role } = this.userDetail.value;

      this.authService
        .grantAuthority(role)
        .pipe(

          tap((res: ResponseResult<Grant>) => {
            this.toastr.success(res.message, '');

            this.onCloseThis.emit({
              statusClose: !this.isOpenThis,
              statusReloadTable: true,
            });
          }),

          catchError((error) => {
            console.log(error);
            return of(null);
          }),

          finalize(() => {
            this.isLoading = false;
            this.isConfirmSave = false;
          })

        )
        .subscribe()

    }, 2000);
  }

}
