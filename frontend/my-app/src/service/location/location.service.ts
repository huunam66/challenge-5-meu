import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment.url-server';
import { District, Province, Ward } from '../../model/location.model';
import { ResponseResult } from '../../model/responseResult.model';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(
    private _http: HttpClient
  ) { }

  public getAllWardsByDistrictId(
    districtId: string,
    districtInclude: boolean = false
  ): Observable<ResponseResult<Ward[]>> {
    return this._http
      .get<ResponseResult<Ward[]>>(
        `${environment.API_WARD_URL}?district_id=${districtId}&district_include=${districtInclude}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getWardById(
    wardId: string,
    provinceInclude: boolean = false
  ): Observable<ResponseResult<Ward>> {
    return this._http
      .get<ResponseResult<Ward>>(
        `${environment.API_WARD_URL}/${wardId}?province_include=${provinceInclude}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getAllDistrictsByProvinceId(
    provinceId: string,
    provinceInclude: boolean = false
  ): Observable<ResponseResult<District[]>> {
    return this._http
      .get<ResponseResult<District[]>>(
        `${environment.API_DISTRICT_URL}?province_id=${provinceId}&province_include=${provinceInclude}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getDistrictById(
    districtId: string
  ): Observable<ResponseResult<District>> {
    return this._http
      .get<ResponseResult<District>>(
        `${environment.API_DISTRICT_URL}/${districtId}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getDistrictAndAllWardsByDistrictId(
    districtId: string,
    provinceInclude: boolean = false
  ): Observable<ResponseResult<District>> {
    return this._http
      .get<ResponseResult<District>>(
        `${environment.API_DISTRICT_URL}/${districtId}/wards?province_include=${provinceInclude}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getAllProvinces(): Observable<ResponseResult<Province[]>> {
    return this._http
      .get<ResponseResult<Province[]>>(
        environment.API_PROVINCE_URL
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getProvinceById(
    provinceId: string
  ): Observable<ResponseResult<Province>> {
    return this._http
      .get<ResponseResult<Province>>(
        `${environment.API_PROVINCE_URL}/${provinceId}`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public getProvinceAndAllDistrictsByProvinceId(
    provinceId: string
  ): Observable<ResponseResult<Province>> {
    return this._http
      .get<ResponseResult<Province>>(
        `${environment.API_PROVINCE_URL}/${provinceId}/districts`
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

}
