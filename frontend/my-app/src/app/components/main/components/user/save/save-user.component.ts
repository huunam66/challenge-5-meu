import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, finalize, of, Subscription, tap } from 'rxjs';
import { environment } from '../../../../../../environments/environment.role-grant';
import { InvalidField } from '../../../../../../model/error.model';
import { ResponseResult } from '../../../../../../model/responseResult.model';
import { User } from '../../../../../../model/user.model';
import { UserService } from '../../../../../../service/user/user.service';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-save-user',
  standalone: true,
  imports: [
    ConfirmBoxComponent,
    FormsModule,
    CommonModule,
    MatIconModule
  ],
  templateUrl: './save-user.component.html',
  styleUrl: './save-user.component.scss'
})
export class SaveUserComponent implements OnInit {

  @Input() forSave!: string;
  @Input() isOpenThis: boolean = false;
  @Output() onCloseThis = new EventEmitter<boolean>();
  @Output() onReloadWhenSaveDone = new EventEmitter();

  ROLE_GRANT: string[] = environment.ROLE_GRANT;

  userSave: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
    role: new FormControl(this.ROLE_GRANT[0])
  });


  isLoading: boolean = false;

  isConfirmExit: boolean = false;

  isConfirmSave: boolean = false;

  private subcription: Subscription;

  ngOnInit(): void {

  }

  constructor(
    private toastr: ToastrService,
    private userService: UserService
  ) { }

  onCancelConfirmSave(): void {
    this.isConfirmSave = false;
  }

  onCancelConfirmExit(): void {
    this.isConfirmExit = false;
  }

  onOkExitFormSave(): void {
    this.isOpenThis = !this.isOpenThis;

    setTimeout(() => {
      this.onCloseThis.emit(this.isOpenThis);
    }, 400);
  }


  onCloseConfirmExit(event: boolean): void {
    this.isConfirmExit = event;
  }

  onSubmitAddNew(): void {

    // console.log(this.productModel);

    this.isConfirmSave = true;
  }

  onSavingProduct(): void {
    this.isLoading = true;
    setTimeout(() => {

      const { email } = this.userSave.value;

      this.userSave.patchValue({
        password: email.charAt(0).toUpperCase() + email.substring(1)
      })

      const userReadySave: User = { ...this.userSave.value }

      this.userService
        .postUser(userReadySave)
        .pipe(

          tap((res: ResponseResult<User>) => {
            this.toastr.success(res.message, '');
            this.onCloseThis.emit(!this.isOpenThis);
            this.onReloadWhenSaveDone.emit();
          }),

          catchError((error: ResponseResult<InvalidField[]>) => {
            const invalidFields = error.data;

            if (invalidFields) {

              invalidFields.forEach((f, index) => {
                setTimeout(() => {
                  this.toastr.error(f.defaultMessage, f.field);
                }, 400 * index);
              })
            }
            else this.toastr.error(error.message, '');

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
