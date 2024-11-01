import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { finalize, tap } from 'rxjs';
import { PayloadToken } from '../../../../../../model/payload-token.model';
import { ResponseResult } from '../../../../../../model/responseResult.model';
import { User } from '../../../../../../model/user.model';
import { UserService } from '../../../../../../service/user.service';
import { JwtService } from '../../../../../../utils/jwt.service';
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
export class TableUserComponent implements OnInit {

  @ViewChild('searchInpEl') search_inpEl!: ElementRef<HTMLInputElement>;

  focus_Search: boolean = false;
  isLoading: boolean = false;
  isOpenSaveForm: boolean = false;
  isOpenDetailForm: boolean = false;
  showDetailCode: string = '';
  searchValue: string = '';

  listUser: User[] = [];
  payloadToken!: PayloadToken;


  constructor(
    private userService: UserService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }

  ngOnInit(): void {
    this.getUsers();
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

  getUsers(): void {
    this.isLoading = true;

    this.userService
      .getAllUser()
      .pipe(
        tap((res: ResponseResult<User[]>) => {

          this.listUser = res.data || [];

          this.showDetailCode = '';
        }),

        finalize(() => {

          this.isLoading = false;
        })
      )
      .subscribe();
  }
}
