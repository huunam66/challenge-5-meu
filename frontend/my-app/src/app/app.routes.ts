import { Routes } from '@angular/router';
import { AuthComponent } from './components/auth/auth.component';
import { SigninComponent } from './components/auth/signin/signin.component';
import { NotfoundComponent } from './components/common/notfound/notfound.component';
import { TableProductComponent } from './components/main/components/product/table/table-product.component';
import { ProfileComponent } from './components/main/components/profile/profile.component';
import { TableUserComponent } from './components/main/components/user/table/table-user.component';
import { MainComponent } from './components/main/main.component';
import { authGuard } from '../guards/auth-guard.guard';

export const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'products',
        pathMatch: 'full'
      },
      {
        path: 'products',
        component: TableProductComponent
      },
      {
        path: 'users',
        component: TableUserComponent,
        // children: [
        //   {
        //     path: 'detail',
        //     component: DetailUserComponent
        //   },
        //   {
        //     path: 'new',
        //     component: DetailUserComponent
        //   }
        // ]
      },
      {
        path: 'user',
        children: [
          {
            path: 'profile',
            component: ProfileComponent
          }
        ]
      }
    ]
  },
  {
    path: 'auth',
    component: AuthComponent,
    canActivate: [authGuard],
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
