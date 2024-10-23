import { Component, OnDestroy } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { AuthApiService } from '../../../../service/auth/auth.service';
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
    private authApiService: AuthApiService,
    private routeService: RouteService,
    private toastr: ToastrService
  ) { }

  private subcription: Subscription;

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }


  onSubmitSignin() {
    this.subcription = this.authApiService
      .signin(this.formLogin.value)
      .pipe(
        finalize(() => this.subcription.unsubscribe())
      )
      .subscribe({
        next: (res) => {
          if (res != null && res.status && res.code == 200) {
            console.log(res)
            const token: string = res.data.token;
            this.localStorageService.setData("token", token);
            this.routeService.navigate('', null);
          }
        },
        error: (err) => {
          console.error(err.error);
          const invalidFields: any = err?.error?.data?.invalidFields;

          if (invalidFields != undefined && invalidFields != null) {
            Object.keys(invalidFields).forEach((key, index) => {
              setTimeout(() => {
                this.toastr.error(invalidFields[key], key);
              }, 400 * index);
            })

            return;
          }

          this.toastr.error(err.error.message, '');
        }
      })
  }


}
