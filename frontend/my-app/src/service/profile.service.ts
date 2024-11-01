import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../environments/environment.url-server';
import { PayloadToken } from '../model/payload-token.model';
import { AvatarResult, Profile, ProfileLocation } from '../model/profile.model';
import { ResponseResult } from '../model/responseResult.model';
import { JwtService } from '../utils/jwt.service';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private payload: PayloadToken;

  constructor(
    private _http: HttpClient,
    private jwtService: JwtService
  ) {
    this.payload = jwtService.getPayload();
  }


  public uploadFile(formData: FormData): Observable<ResponseResult<AvatarResult>> {
    // console.log(`${urlApiGetByPage}?limit=${limit}&page=${page}`);

    return this._http
      .post<ResponseResult<AvatarResult>>(
        `${environment.API_PROFILE_URL}/${this.payload?.sub}/upload`,
        formData
      )
      .pipe(
        catchError((error) => throwError(() => error))
      );
  }

  public getProfile(email: string): Observable<ResponseResult<Profile>> {
    return this._http
      .get<ResponseResult<Profile>>(
        `${environment.API_PROFILE_URL}/${email}`
      )
      .pipe(
        catchError((error) => throwError(() => error))
      );
  }


  public postProfile(profile: Profile): Observable<ResponseResult<Profile>> {
    return this._http
      .post<ResponseResult<Profile>>(
        `${environment.API_PROFILE_URL}/${this.payload.sub}`,
        profile
      )
      .pipe(
        catchError((error) => throwError(() => error))
      );
  }

  public postProfileLocation(profileLocation: ProfileLocation): Observable<ResponseResult<ProfileLocation>> {
    return this._http
      .post<ResponseResult<ProfileLocation>>(
        `${environment.API_PROFILE_URL}/${this.payload.sub}/location`,
        profileLocation
      )
      .pipe(
        catchError((error) => throwError(() => error))
      );
  }
}
