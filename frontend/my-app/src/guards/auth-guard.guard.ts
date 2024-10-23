import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { PayloadToken } from '../model/payload-token.model';
import { JwtService } from '../utils/jwt.service';

export const authGuard: CanActivateFn = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
) => {

  console.log(state.url)

  // const router: Router = inject(Router);
  // const jwtService: JwtService = inject(JwtService);

  // const protectedRoutes: string[] = ['/products', '/users', '/user'];

  // if (!protectedRoutes.includes(state.url)) return true;

  // const checkLocalStorage = (): Promise<boolean> => {
  //   return new Promise((resolve) => {
  //     const token = localStorage.getItem('token');
  //     resolve(token != null);
  //   });
  // };

  // const hasToken = await checkLocalStorage();

  // if (!hasToken) {
  //   router.navigate(['/auth/signin']);
  //   return hasToken;
  // }

  // const tokenPayload: PayloadToken = jwtService.getPayload();

  // // console.log('Payload:', tokenPayload);

  // if (!tokenPayload) {
  //   router.navigate(['/auth/signin']);
  //   return false;
  // }

  // const adminRoutes: string[] = ['/user'];
  // if (tokenPayload.scp === 'USER' && adminRoutes.includes(state.url)) {
  //   router.navigate(['/auth/signin']);
  //   return false;
  // }

  return true;
};
