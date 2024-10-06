import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { JwtService } from './../../../../service/utils/jwt.service';
import { PayloadTokenModel } from '../../../../model/payload-token.model';
import { RouterLink, RouterLinkActive } from '@angular/router';

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
  }

  onLogout(): void{
    this.jwtService.logOut();
  }
}
