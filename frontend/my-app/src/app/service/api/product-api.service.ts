import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../../model/product/product.model';
import { LocalStorageService } from '../utils/local-storage.service';

const URL_REQUEST = 'http://localhost:8080/api/products';


@Injectable(
  {
    providedIn: 'root'
  }
)
export class ProductApiService {

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {}


  private httpHeaders(): any{
    const token: any = this.localStorageService.getData("token");

    return {
      headers: new HttpHeaders()
                  .append('Content-Type', 'application/json')
                  .append('Authorization', `Bearer ${token}`),
      withCredentials: true
    }
  }

  public filter(limit: number, page: number): Observable<any> {
    // console.log(`${urlApiGetByPage}?limit=${limit}&page=${page}`);
    return this.httpClient
      .get<any>(`${URL_REQUEST}/filter?limit=${limit}&page=${page}`, this.httpHeaders());
  }

  public get(code: string): Observable<any> {
    return this.httpClient
      .get<any>(`${URL_REQUEST}/${code}`, this.httpHeaders());
  }

  public search(keyword: string): Observable<any> {

    return this.httpClient
      .get<any>(`${URL_REQUEST}/search/${keyword}`, this.httpHeaders());
  }

  public post(product: Product): Observable<any> {
    // console.log(product)
    return this.httpClient
      .post<any>(URL_REQUEST, product, this.httpHeaders());
  }

  public put(product: Product, code: string): Observable<any> {

    return this.httpClient
      .put<any>(`${URL_REQUEST}/${code}`, product, this.httpHeaders());
  }

  public delete(code: string) {

    return this.httpClient
      .delete<any>(`${URL_REQUEST}/${code}`, this.httpHeaders());
  }
}
