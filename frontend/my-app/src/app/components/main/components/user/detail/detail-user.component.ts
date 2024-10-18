import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { PayloadToken } from '../../../../../model/common/payload-token.model';
import { Grant } from '../../../../../model/user/grant.model';
import { User } from '../../../../../model/user/user.model';
import { AuthApiService } from '../../../../../service/api/auth-api.service';
import { UserApiService } from '../../../../../service/api/user-api.service';
import { JwtService } from '../../../../../service/utils/jwt.service';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-detail-user',
  standalone: true,
  imports: [ConfirmBoxComponent, FormsModule, CommonModule, MatIconModule],
  templateUrl: './detail-user.component.html',
  styleUrl: './detail-user.component.scss'
})
export class DetailUserComponent implements OnInit, OnDestroy {
  userDetail: User = new User();
  userEdit: Grant = new Grant();

  ROLE_STORE: string[] = ["USER", "ADMIN"];

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

  payloadToken!: PayloadToken;

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
        this.injectUser(res);
      })
  }

  ngOnDestroy(): void {
      this.subcription?.unsubscribe();
  }

  injectUser(res: any): void{
    const user = res.data.user;

    this.userDetail.email = this.userEdit.email = user.email;
    this.userDetail.role = user.authority.name;

    const isSuperAdmin: boolean = this.userDetail.role === 'SUPER_ADMIN';
    const isAdmin: boolean = this.userDetail.role === 'ADMIN';

    // console.log('isSuperAdmin: ' + isSuperAdmin);
    // console.log('isAdmin: ' + isAdmin);

    this.userEdit.role = this.currentRole = isSuperAdmin ? "SUPER ADMIN" : isAdmin ? "ADMIN" : "USER";
    // console.log(isSuperAdmin ? "SUPER ADMIN" : isAdmin ? "ADMIN" : "USER")
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
        .delete(this.userDetail.email)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedDeleteProduct(res),
          error: (err) => {
            this.isLoading = false;
            this.isConfirmDelete = false;
            this.toastr.error(err.error.message, '');
          }
        });
    }, 2000);
  }

  onResponsedDeleteProduct(res: any): void {
    // console.log(res);
    this.isLoading = false;
    this.isConfirmDelete = false;
    if (res.code != 200) {
      this.toastr.error(res.message, 'Lỗi xóa tài khoản!');
    } else {
      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
      this.toastr.success(res.message, '');
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
        .grant(this.userEdit)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedSavingProduct(res),
          error: (err) => {
            console.log(err);
            this.isLoading = false;
            this.isConfirmSave = false;
            this.toastr.error(err.error.message, '');
          }
        });
    }, 2000);
  }

  onResponsedSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;
    if (res.code != 200) {
      this.toastr.error('Lỗi phân quyền!', '');
    } else {
      this.toastr.success(res.message, '');

      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
    }
  }
}
