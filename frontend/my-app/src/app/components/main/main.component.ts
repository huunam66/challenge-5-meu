import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JwtService } from '../../service/utils/jwt.service';
import { TableProductComponent } from '../product/table/table-product.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HeaderComponent } from './header/header.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [HeaderComponent, DashboardComponent, TableProductComponent, RouterOutlet],
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
