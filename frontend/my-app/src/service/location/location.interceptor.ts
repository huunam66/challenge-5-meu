import { HttpInterceptorFn } from '@angular/common/http';

export const locationInterceptor: HttpInterceptorFn = (req, next) => {
  console.log(req);
  return next(req);
};
