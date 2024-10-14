import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { SigninComponent } from './components/auth/signin/signin.component';
import { NotfoundComponent } from './components/common/notfound/notfound.component';
import { MainComponent } from './components/main/main.component';
import { TableProductComponent } from './components/product/table/table-product.component';
import { DetailUserComponent } from './components/user/detail/detail-user.component';
import { TableUserComponent } from './components/user/table/table-user.component';
import { AuthGuardService } from './service/auth-guard.service';

export const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuardService],
    canActivateChild: [AuthGuardService],
    children: [
      {
        path: '',
        redirectTo: 'product',
        pathMatch: 'full'
      },
      {
        path: 'product',
        component: TableProductComponent
      },
      {
        path: 'user',
        component: TableUserComponent,
        children: [
          {
            path: 'detail',
            component: DetailUserComponent
          },
          {
            path: 'new',
            component: DetailUserComponent
          }
        ]
      }
    ]
  },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: 'signin',
        component: SigninComponent
      },
      {
        path: '**',
        component: NotfoundComponent
      }
    ]
  },
  {
    path: '**',
    component: NotfoundComponent
  }
];
