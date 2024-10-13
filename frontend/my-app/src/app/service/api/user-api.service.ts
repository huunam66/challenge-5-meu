import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { UserModel } from '../../model/user.model';
import { LocalStorageService } from '../utils/local-storage.service';

const URL_REQUEST = 'http://localhost:8080/api/users';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {

  private subcription: Subscription;

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) { }


  private httpHeaders(): any{
    const token: any = this.localStorageService.getData("token");

    return {
      headers: new HttpHeaders()
                  .append('Content-Type', 'application/json')
                  .append('Authorization', `Bearer ${token}`),
      withCredentials: true
    }
  }


  public getAll(): Observable<any>{
    return this.httpClient.get<any>(URL_REQUEST, this.httpHeaders());
  }

  public get(email: string): Observable<any>{
    return this.httpClient.get<any>(`${URL_REQUEST}/${email}`, this.httpHeaders());
  }

  public post(userModel: UserModel): Observable<any>{
    return this.httpClient.post<any>(URL_REQUEST, userModel, this.httpHeaders());
  }

  public delete(email: string): Observable<any>{
    return this.httpClient.delete<any>(`${URL_REQUEST}/${email}`, this.httpHeaders());
  }
}
