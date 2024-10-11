import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { PayloadTokenModel } from '../../../../model/payload-token.model';
import { JwtService } from './../../../../service/utils/jwt.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MatIconModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {

  public payloadToken!: PayloadTokenModel;


  constructor(
    private jwtService: JwtService,
  ){
    this.payloadToken = this.jwtService.getPayload();
    console.log(this.payloadToken);
  }

  onLogout(): void{
    this.jwtService.logOut();
  }
}
