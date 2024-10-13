import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { GrantModel } from '../../../model/grant.model';
import { PayloadTokenModel } from '../../../model/payload-token.model';
import { UserModel } from '../../../model/user.model';
import { AuthApiService } from '../../../service/api/auth-api.service';
import { UserApiService } from '../../../service/api/user-api.service';
import { JwtService } from '../../../service/utils/jwt.service';
import { ConfirmBoxComponent } from "../../common/confirm-box/confirm-box.component";

@Component({
  selector: 'app-detail-user',
  standalone: true,
  imports: [ConfirmBoxComponent, FormsModule, CommonModule, MatIconModule],
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.scss'
})
export class DetailUserComponent implements OnInit, OnDestroy {
  userModelDetail: UserModel = new UserModel();
  userModelEdit: GrantModel = new GrantModel();

  ROLE_STORE: string[] = ["ADMIN", "USER"];

  currentRole!: string;

  isLoading: boolean;

  @Input()
  detailCode!: string;

  @Input()
  isDetailView!: boolean;

  @Input()
  isEditView!: boolean;

  @Input()
  isOpenThis: boolean;

  @Output()
  onCloseThis = new EventEmitter<any>();

  isConfirmExit: boolean = false;

  isConfirmSave: boolean = false;

  isConfirmDelete: boolean = false;

  payloadToken!: PayloadTokenModel;

  constructor(
    private toastr: ToastrService,
    private userApiService: UserApiService,
    private authApiService: AuthApiService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }

  private subcription: Subscription;

  ngOnInit(): void {
    this.subcription = this.userApiService
      .get(this.detailCode)
      .pipe(
        finalize(() => this.subcription.unsubscribe())
      )
      .subscribe((res: any) => {
        this.injectUserModel(res);
      })
  }

  ngOnDestroy(): void {
      this.subcription?.unsubscribe();
  }

  injectUserModel(res: any): void{
    const user = res.responseEntity.user;

    this.userModelDetail.email = this.userModelEdit.email = user.email;
    this.userModelDetail.role = user.authorities.map((role: any) => role.authority);

    const isSuperAdmin: boolean = this.userModelDetail.role.includes("ROLE_SUPER_ADMIN");
    const isAdmin: boolean = this.userModelDetail.role.includes("ROLE_ADMIN");

    console.log('isSuperAdmin: ' + isSuperAdmin);
    console.log('isAdmin: ' + isAdmin);

    this.userModelEdit.role = this.currentRole = isSuperAdmin ? "SUPER ADMIN" : isAdmin ? "ADMIN" : "USER";
    console.log(isSuperAdmin ? "SUPER ADMIN" : isAdmin ? "ADMIN" : "USER")
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
      this.subcription = this.userApiService
        .delete(this.userModelDetail.email)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedDeleteProduct(res),
          error: (err) => {
            this.isLoading = false;
            this.isConfirmDelete = false;
            this.toastr.error(err.error.message, '', {
              timeOut: 5000,
              closeButton: true,
            });
          }
        });
    }, 2000);
  }

  onResponsedDeleteProduct(res: any): void {
    // console.log(res);
    this.isLoading = false;
    this.isConfirmDelete = false;
    if (res.code != 200) {
      this.toastr.error(res.message, 'Lỗi xóa tài khoản!', {
        timeOut: 5000,
        closeButton: true,
      });
    } else {
      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
      this.toastr.success(res.message, '', {
        timeOut: 5000,
        closeButton: true,
      });
    }
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
      this.subcription = this.authApiService
        .grant(this.userModelEdit)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedSavingProduct(res),
          error: (err) => {
            console.log(err);
            this.isLoading = false;
            this.isConfirmSave = false;
            this.toastr.error(err.error.message, '', {
              closeButton: true,
              timeOut: 5000,
            });
          }
        });
    }, 2000);
  }

  onResponsedSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;
    if (res.code != 200) {
      this.toastr.error('Lỗi phân quyền!', '', {
        closeButton: true,
        timeOut: 5000,
      });
    } else {
      this.toastr.success(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });

      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
    }
  }
}
