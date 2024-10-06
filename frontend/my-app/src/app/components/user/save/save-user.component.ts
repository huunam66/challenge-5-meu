import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { UserApiService } from '../../../service/api/user-api.service';
import { ConfirmBoxComponent } from "../../common/confirm-box/confirm-box.component";
import { UserModel } from './../../../model/user.model';

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
export class SaveUserComponent {
  public userModel: UserModel = new UserModel();

  public isLoading: boolean = false;

  @Input()
  public forSave!: string;

  @Input()
  public isOpenThis: boolean = false;

  @Output()
  public onCloseThis = new EventEmitter<boolean>();

  @Output()
  public onReloadWhenSaveDone = new EventEmitter();

  public isConfirmExit: boolean = false;

  public isConfirmSave: boolean = false;

  constructor(
    private toastr: ToastrService,
    private userApiService: UserApiService
  ) {}

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

  onClose(): void {

    this.onOkExitFormSave();
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
      this.userApiService
        .post(this.userModel)
        .subscribe((res) => this.onResponseSavingProduct(res), (err) => {
          this.isLoading = false;
          this.isConfirmSave = false;
          console.log(err);
          const invalidFields: any = err?.error?.responseEntity?.invalidFields;

          if(invalidFields != undefined && invalidFields != null){
            Object.keys(invalidFields).forEach((key, index) => {
              setTimeout(() => {
                this.toastr.error(invalidFields[key], key, {
                  closeButton: true,
                  timeOut: 5000,
                });
              }, 400 * index);
            })

            return;
          }

          this.toastr.error(err.error.message, '', {
            closeButton: true,
            timeOut: 5000,
          });
        });
    }, 2000);
  }

  onResponseSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;

    console.log(res);
    if (res.code != 201) {
      this.toastr.error(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });
    } else {
      this.toastr.success(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });

      this.onCloseThis.emit(!this.isOpenThis);
      this.onReloadWhenSaveDone.emit();
    }
  }
}
