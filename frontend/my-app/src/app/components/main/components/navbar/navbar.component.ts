import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { JwtService } from '../../../../../utils/jwt.service';
import { PayloadToken } from '../../../../../model/payload-token.model';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [MatIconModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {

  public payloadToken!: PayloadToken;


  constructor(
    private jwtService: JwtService,
    private router: Router
  ){
    this.payloadToken = this.jwtService.getPayload();
    // console.log(this.payloadToken);
  }

  onLogout(): void{
    this.jwtService.logOut();
    this.router.navigate(['/auth/signin'])
  }
}
