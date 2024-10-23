import { HttpInterceptorFn } from '@angular/common/http';

export const productInterceptor: HttpInterceptorFn = (req, next) => {
  console.log(req);
  return next(req);
};
