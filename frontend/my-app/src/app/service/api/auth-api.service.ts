import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Grant } from '../../model/user/grant.model';
import { User } from '../../model/user/user.model';
import { JwtService } from '../utils/jwt.service';

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
    }
  }


  public signin(user: User): Observable<any>{
    return this.httpClient.post(`${URL_REQUEST}/signin`, user, this.httpHeaders())
  }

  public grant(grant: Grant): Observable<any>{
    console.log(grant);
    const httpHeaders = this.httpHeaders();

    return this.httpClient.put(`${URL_REQUEST}/grant`, grant, {
                                                ...httpHeaders,
                                                headers: httpHeaders.headers.append('Authorization', `Bearer ${this.jwtService.getToken()}`),
                                                withCredentials: true
                          });
  }

}
