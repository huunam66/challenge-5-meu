import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LocalStorageService } from './local-storage.service';
import { RouteService } from './route.service';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class JwtService {

  private jwtHelperSerive = new JwtHelperService();

  constructor(
    private localStorageService: LocalStorageService,
    private routeService: RouteService
  ) { }


  public getToken() {
    return this.localStorageService?.getData("token");
  }

  public checkToken(): boolean{
    const payload: any = this.getPayload();

    return payload != null;
  }

  public logOut(): void{
    this.localStorageService?.clearData();
  }

  public getPayload(): any{
    const token: any = this.getToken();
    if(token == null || token == "" || token == undefined)
      return null;

    const payload = this.jwtHelperSerive.decodeToken(token);

    const exp = new Date(payload.exp * 1000);
    const timeNow = new Date();

    if(timeNow > exp){
      this.localStorageService.removeData('token');
      return null;
    }

    return payload;
  }
}
