import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import { environment } from '../../environments/environment.url-server';
import { ResponseResult } from '../../model/responseResult.model';
import { User } from '../../model/user.model';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private _http: HttpClient
  ) { }


  public getAllUser(): Observable<ResponseResult<User[]>> {
    return this._http.get<ResponseResult<User[]>>(
      environment.API_USER_URL
    )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public postUser(user: User): Observable<ResponseResult<User>> {
    return this._http.post<ResponseResult<User>>(
      environment.API_USER_URL,
      user
    )
      .pipe(
        catchError((error) => of({ ...error }))
      );
  }

  public getUserByEmail(email: string): Observable<ResponseResult<User>> {
    return this._http.get<ResponseResult<User>>(
      `${environment.API_USER_URL}/${email}`
    )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }

  public deleteUserByEmail(email: string): Observable<ResponseResult<never>> {
    return this._http.delete<ResponseResult<never>>(
      `${environment.API_USER_URL}/${email}`
    )
      .pipe(
        catchError((error) => throwError(() => new Error(error)))
      );
  }
}
