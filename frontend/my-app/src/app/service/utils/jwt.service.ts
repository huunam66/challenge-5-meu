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


  public getToken(): any {
    return this.localStorageService.getData("token");
  }

  public checkToken(): void{
    const token: any = this.getToken();
    if(token == null || token == "" || token == undefined)
      this.routeService.navigate('/auth/signin', null)

    console.log(token)
  }

  public logOut(): void{
    this.localStorageService.clearData();
    this.checkToken();
  }

  public getPayload(): any{
    const token: any = this.getToken();
    if(token == null || token == "" || token == undefined)
      return null;

    const payload = this.jwtHelperSerive.decodeToken(token);

    console.log("payload.iat: " + payload.iat)
    console.log("payload.exp: " + payload.exp)

    console.log("payload.iat: " + new Date(payload.iat))
    console.log("payload.exp: " + new Date(payload.exp))

    return payload;
  }
}
