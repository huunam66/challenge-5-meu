import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment.url-server';
import { Product } from '../../model/product.model';
import { PageableFilter, ResponseResult } from '../../model/responseResult.model';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(
    private _http: HttpClient
  ) { }


  public filterProductByLimitAndPage(
    limit: number,
    page: number
  ): Observable<ResponseResult<PageableFilter<Product>>> {
    // console.log(`${urlApiGetByPage}?limit=${limit}&page=${page}`);
    return this._http
      .get<ResponseResult<PageableFilter<Product>>>(
        `${environment.API_PRODUCT_URL}/filter?limit=${limit}&page=${page}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getProductByCode(code: string): Observable<ResponseResult<Product>> {
    return this._http
      .get<ResponseResult<Product>>(
        `${environment.API_PRODUCT_URL}/${code}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public searchProduct(keyword: string): Observable<ResponseResult<Product[]>> {

    return this._http
      .get<ResponseResult<Product[]>>(
        `${environment.API_PRODUCT_URL}/search/${keyword}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public postProduct(product: Product): Observable<ResponseResult<Product>> {
    // console.log(product)
    return this._http
      .post<ResponseResult<Product>>(
        environment.API_PRODUCT_URL,
        product
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public putProduct(product: Product, code: string): Observable<ResponseResult<Product>> {

    return this._http
      .put<ResponseResult<Product>>(
        `${environment.API_PRODUCT_URL}/${code}`,
        product
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public deleteProduct(code: string): Observable<ResponseResult<never>> {

    return this._http
      .delete<ResponseResult<never>>(
        `${environment.API_PRODUCT_URL}/${code}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }
}
