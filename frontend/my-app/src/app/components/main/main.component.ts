import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JwtService } from '../../service/utils/jwt.service';
import { TableProductComponent } from '../product/table/table-product.component';
import { NavbarComponent } from "./navbar/navbar.component";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [TableProductComponent, RouterOutlet, NavbarComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent{


  constructor(
    private jwtService: JwtService
  ){
    jwtService.checkToken();
  }

}
