import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JwtService } from '../utils/jwt.service';

const URL_REQUEST_PROVINCES = 'http://localhost:8080/api/location/provinces';
const URL_REQUEST_DISTRICTS = 'http://localhost:8080/api/location/districts';
const URL_REQUEST_WARDS = 'http://localhost:8080/api/location/wards';

@Injectable({
  providedIn: 'root'
})
export class LocationApiService {

  constructor(
    private HttpClient: HttpClient,
    private jwtService: JwtService,
  ) { }

  private httpHeaders(): any{
    const token: any = this.jwtService.getToken();

    return {
      headers: new HttpHeaders()
                  .append('Content-Type', 'application/json')
                  .append('Authorization', `Bearer ${token}`),
      withCredentials: true
    }
  }

  public getAllWardsByDistrictId(districtId: string, districtInclude: boolean = false): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_WARDS}?district_id=${districtId}&district_include=${districtInclude}`
      , this.httpHeaders()
    );
  }

  public getWardById(wardId: string, provinceInclude: boolean = false): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_WARDS}/${wardId}?province_include=${provinceInclude}`,
      this.httpHeaders()
    );
  }

  public getAllDistrictsByProvinceId(provinceId: string, provinceInclude: boolean = false): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_DISTRICTS}?province_id=${provinceId}&province_include=${provinceInclude}`,
      this.httpHeaders()
    );
  }

  public getDistrictById(districtId: string): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_DISTRICTS}/${districtId}`,
      this.httpHeaders()
    )
  }

  public getDistrictAndAllWardsByDistrictId(districtId: string, provinceInclude: boolean = false): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_DISTRICTS}/${districtId}/wards?province_include=${provinceInclude}`,
      this.httpHeaders()
    );
  }

  public getAllProvinces(): Observable<any>{
    return this.HttpClient.get<any>(
      URL_REQUEST_PROVINCES,
      this.httpHeaders()
    );
  }

  public getProvinceById(provinceId: string): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_PROVINCES}/${provinceId}`,
      this.httpHeaders()
    );
  }

  public getProvinceAndAllDistrictsByProvinceId(provinceId: string): Observable<any>{
    return this.HttpClient.get<any>(
      `${URL_REQUEST_PROVINCES}/${provinceId}/districts`,
      this.httpHeaders()
    );
  }

}
