import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment.url-server';
import { ResponseResult } from '../../model/responseResult.model';
import { Authenticated, Grant, User } from '../../model/user.model';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthService {

  constructor(
    private _http: HttpClient
  ) { }


  public signin(user: User): Observable<ResponseResult<Authenticated>> {
    return this._http
      .post<ResponseResult<Authenticated>>(
        `${environment.API_AUTHORIZE_URL}/signin`,
        user
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public grantAuthority(grant: Grant): Observable<ResponseResult<Grant>> {

    return this._http
      .put<ResponseResult<Grant>>(
        `${environment.API_AUTHORIZE_URL}/grant`,
        grant
      )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

}
