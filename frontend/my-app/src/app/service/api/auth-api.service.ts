import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserModel } from '../../model/user.model';
import { JwtService } from '../utils/jwt.service';
import { GrantModel } from './../../model/grant.model';

const URL_REQUEST = 'http://localhost:8080/api/auth';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthApiService{

  constructor(
    private httpClient: HttpClient,
    private jwtService: JwtService
  ) { }

  private httpHeaders(): any{

    return {
      headers: new HttpHeaders()
                  .append('Content-Type', 'application/json'),
      withCredentials: true
    }
  }


  public signin(userModel: UserModel): Observable<any>{
    return this.httpClient.post(`${URL_REQUEST}/signin`, userModel, this.httpHeaders()).pipe()
  }

  public grant(grantModel: GrantModel): Observable<any>{
    console.log(grantModel);
    const httpHeaders = this.httpHeaders();

    return this.httpClient.put(`${URL_REQUEST}/grant`, grantModel, {
                                                ...httpHeaders,
                                                headers: httpHeaders.headers.append('Authorization', `Bearer ${this.jwtService.getToken()}`)
                          }).pipe();
  }

}
