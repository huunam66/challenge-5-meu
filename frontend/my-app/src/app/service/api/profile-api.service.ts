import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PayloadToken } from '../../model/common/payload-token.model';
import { Profile } from '../../model/user/profile.model';
import { LocalStorageService } from '../utils/local-storage.service';
import { JwtService } from './../utils/jwt.service';

const URL_REQUEST = "http://localhost:8080/api/profile";

@Injectable({
  providedIn: 'root'
})
export class ProfileApiService {

  private token: string;

  private payload: PayloadToken;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService,
    private jwtService: JwtService
  ) {
    this.token = this.localStorageService.getData("token") || "";
    this.payload = jwtService.getPayload();
  }

  private httpHeaders(): any{

    return {
      headers: new HttpHeaders()
                  .append('Authorization', `Bearer ${this.token}`)
                  .append('Content-Type', 'application/json'),
      withCredentials: true
    }
  }

  public uploadFile(formData: FormData): Observable<any> {
    // console.log(`${urlApiGetByPage}?limit=${limit}&page=${page}`);

    const http = this.httpHeaders();

    return this.httpClient
      .post<any>(`${URL_REQUEST}/${this.payload?.sub}/upload`, formData ,  {
        ...http,
        headers: http.headers.delete('Content-Type')
      });
  }

  public getProfile(email: string): Observable<any> {
    return this.httpClient.get<any>(`${URL_REQUEST}/${email}`, this.httpHeaders());
  }


  public postProfile(profile: Profile): Observable<any>{
    return this.httpClient.post<any>(`${URL_REQUEST}/${this.payload.sub}`, profile, this.httpHeaders());
  }

  public postProfileLocation(profile: Profile): Observable<any>{
    return this.httpClient.post<any>(`${URL_REQUEST}/${this.payload.sub}/location`, profile.profileLocation, this.httpHeaders());
  }
}
