import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { productInterceptor } from './product.interceptor';
import { ProductService } from './product.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    ProductService,
    {
      provide: HTTP_INTERCEPTORS,
      useValue: productInterceptor,
      multi: true
    }
  ]
})
export class ProductModule { }
