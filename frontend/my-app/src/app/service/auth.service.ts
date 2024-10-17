import { Injectable } from '@angular/core';
import { JwtService } from './utils/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn: boolean = false;

  constructor(
    private jwtService: JwtService
  ) { }


  public isAuthenticated(): Promise<boolean>{
    return new Promise<boolean>(
      (resolve, rejects) => {
        resolve(this.jwtService.checkToken())
      }
    );
  }

  public logIn(): void{
    this.loggedIn = true;
  }

  public logOut(): void{
    this.loggedIn = false;
  }
}
