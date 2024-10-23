import { HttpInterceptorFn } from '@angular/common/http';

export const profileInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
