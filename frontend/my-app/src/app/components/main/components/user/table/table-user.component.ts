import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { finalize, Subscription } from 'rxjs';
import { UserApiService } from '../../../../../../service/user/user.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { PayloadToken } from '../../../../../model/payload-token.model';
import { User } from '../../../../../model/user.model';
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
  listUser: User[] = new Array();

  @ViewChild('searchInpEl')
  search_inpEl!: ElementRef<HTMLInputElement>;

  payloadToken!: PayloadToken;

  private subcription: Subscription;

  ngOnDestroy(): void {
    this.subcription.unsubscribe();
  }

  constructor(
    private userApiService: UserApiService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
    console.log(this.payloadToken)
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

    this.injectListUser(res);

    this.isLoading = false;
    this.showDetailCode = '';
  }

  injectListUser(res: any): void {
    console.log(res)
    const users = res.data.users;

    this.listUser = new Array();

    users.forEach((u: any) => {
      const user = new User();
      user.email = u.email;
      user.role = u.authority.name;

      console.log(user)

      // console.log(user);
      this.listUser.push(user);
    })
  }



  getProductOnInitComponent(): void {
    this.getUsers();
  }

  getUsers(): void {
    // console.log('hello')

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
    // console.log(code);
    this.showDetailCode = code;
    this.isOpenDetailForm = true;
  }
}
