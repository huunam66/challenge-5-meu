import { Component, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, of, Subscription, tap, throwError } from 'rxjs';
import { InvalidField } from '../../../../model/error.model';
import { ResponseResult } from '../../../../model/responseResult.model';
import { Authenticated } from '../../../../model/user.model';
import { AuthService } from '../../../../service/auth/auth.service';
import { LocalStorageService } from '../../../../utils/local-storage.service';
import { RouteService } from '../../../../utils/route.service';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [MatIconModule, ReactiveFormsModule],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.css'
})
export class SigninComponent implements OnDestroy {

  public formLogin: FormGroup = new FormGroup({
    email: new FormControl(""),
    password: new FormControl("")
  });

  constructor(
    private localStorageService: LocalStorageService,
    private authService: AuthService,
    private routeService: RouteService,
    private toastr: ToastrService
  ) { }

  private subcription: Subscription;

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }


  onSubmitSignin() {
    this.authService
      .signin(this.formLogin.value)
      .pipe(
        tap((res: ResponseResult<Authenticated>) => {
          const token: string = res.data?.token || '';
          this.localStorageService.setData("token", token);
          this.routeService.navigate('', null);
        }),
        catchError((error: ResponseResult<InvalidField[]>) => {
          error.data?.forEach(f => this.toastr.error(f.defaultMessage, f.field))

          return throwError(() => error);
        })
      )
      .subscribe()
  }


}
