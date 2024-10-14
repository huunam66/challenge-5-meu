import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { finalize, Subscription } from 'rxjs';
import { PayloadTokenModel } from '../../../model/payload-token.model';
import { UserModel } from '../../../model/user.model';
import { UserApiService } from '../../../service/api/user-api.service';
import { JwtService } from '../../../service/utils/jwt.service';
import { DetailUserComponent } from '../detail/detail-user.component';
import { SaveUserComponent } from "../save/save-user.component";

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [
    DetailUserComponent,
    SaveUserComponent,
    MatIconModule,
    CommonModule,
    FormsModule
  ],
  templateUrl: './table-user.component.html',
  styleUrl: './table-user.component.scss'
})
export class TableUserComponent implements OnDestroy {
// State component
focus_Search: boolean = false;
isLoading: boolean = false;

isOpenSaveForm: boolean = false;
isOpenDetailForm: boolean = false;

showDetailCode: string = '';

// Binding component
searchValue: string = '';
ListUserModel: UserModel[] = new Array();

@ViewChild('searchInpEl')
search_inpEl!: ElementRef<HTMLInputElement>;

payloadToken!: PayloadTokenModel;

private subcription: Subscription;

ngOnDestroy(): void {
    this.subcription.unsubscribe();
}

constructor(
  private userApiService: UserApiService,
  private jwtService: JwtService
) {
  this.payloadToken = jwtService.getPayload();
}

ngOnInit(): void {
  this.getProductOnInitComponent();
}

onCloseSaveForm(event: boolean): void {
  this.isOpenSaveForm = event;
}

onCloseDetailForm(event: any): void {
  this.isOpenDetailForm = event.statusIsOpen;
  if (event.statusReloadTable) {
    this.getUsers();
  }
}

fillDataByPage(res: any): void {

  this.injectListUserModel(res);

  this.isLoading = false;
  this.showDetailCode = '';
}

injectListUserModel(res: any): void{
  const users = res.responseEntity.users;

  this.ListUserModel = new Array();

  users.forEach((user: any) =>{
    const userModel = new UserModel();
    userModel.email = user.email;
    userModel.role = user.authorities.map((role: any) => {
      const authority = role.authority;
      const autsplt = authority.split("_");
      return (autsplt[1] + " " + (autsplt[2] || "")).trim();
    });

    console.log(userModel);
    this.ListUserModel.push(userModel);
  })
}



getProductOnInitComponent(): void {
  this.getUsers();
}

getUsers(): void {
  console.log('hello')

  this.isLoading = true;
  this.subcription = this.userApiService
    .getAll()
    .pipe(
      finalize(() => {
        this.subcription.unsubscribe();
      })
    )
    .subscribe((res: any) => this.fillDataByPage(res));
}


onClickLabelSearchTemp(): void {
  // console.log(this.search_inpEl.nativeElement);
  this.search_inpEl.nativeElement.focus();
}

onFocusSearch(): void {
  this.focus_Search = true;
}


onFocusOut(): void {
  if (this.searchValue !== '') return;

  this.focus_Search = false;
}

onShowDetail(code: string) {
  console.log(code);
  this.showDetailCode = code;
  this.isOpenDetailForm = true;
}
}
