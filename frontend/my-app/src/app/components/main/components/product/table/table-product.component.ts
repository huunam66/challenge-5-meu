import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { finalize, tap } from 'rxjs';
import { PayloadToken } from '../../../../../../model/payload-token.model';
import { Product } from '../../../../../../model/product.model';
import { PageableFilter, ResponseResult } from '../../../../../../model/responseResult.model';
import { ProductService } from '../../../../../../service/product.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { RouteService } from '../../../../../../utils/route.service';
import { LoadingComponent } from '../../../../common/loading/loading.component';
import { DetailProductComponent } from '../detail/detail-product.component';
import { SaveProductComponent } from '../save/save-product.component';

@Component({
  selector: 'app-table-product',
  standalone: true,
  imports: [
    MatIconModule,
    FormsModule,
    CommonModule,
    SaveProductComponent,
    DetailProductComponent,
    LoadingComponent
  ],
  templateUrl: './table-product.component.html',
  styleUrl: './table-product.component.scss',
})
export class TableProductComponent implements OnInit {

  @ViewChild('searchInpEl') search_inpEl!: ElementRef<HTMLInputElement>;

  payloadToken!: PayloadToken;

  focus_Search: boolean = false;
  isLoading: boolean = false;
  isOpenSaveForm: boolean = false;
  isOpenDetailForm: boolean = false;
  showDetailCode: string = '';
  searchValue: string = '';

  pageableFilter: PageableFilter<Product[]>;

  constructor(
    private productService: ProductService,
    private router: Router,
    private routeService: RouteService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }


  ngOnInit(): void {
    this.routeService.query((query: any) => {
      if (query.search) {
        this.searchValue = query.search;
        this.focus_Search = true;
        this.searchProduct();
      } else this.getProductOnInitComponent();
    });
  }

  onCloseSaveForm(event: boolean): void {
    this.isOpenSaveForm = event;
  }

  onCloseDetailForm(event: any): void {
    this.isOpenDetailForm = event.statusIsOpen;
    if (event.statusReloadTable) {
      this.getProducts(this.pageableFilter.pageNumber || 1);
    }
  }

  onPrevPage(): void {
    const hastPrevPage = this.pageableFilter.hasPreviousPage;

    if (!hastPrevPage) return;

    const prevPage: number = this.pageableFilter.pageNumber || 2;

    this.router.navigate([`/products?page=${prevPage - 1}`]);
  }

  onNextPage(): void {

    const hastNextPage = this.pageableFilter.hasNextPage;

    if (!hastNextPage) return;

    const nextPage: number = this.pageableFilter.pageNumber || 0;

    this.router.navigate([`/products?page=${nextPage + 1}`]);
  }

  onHeadPage(): void {
    const headPage: number = 1;

    this.router.navigate([`/products?page=${headPage}`])
  }

  onTailPage(): void {
    const tailPage: number = this.pageableFilter.totalPages || 1;

    this.router.navigate([`/products?page=${tailPage}`])
  }

  getProductOnInitComponent(): void {
    this.routeService.query((query: any) => {
      if (query.page) {
        this.getProducts(Number.parseInt(query.page));
      } else this.getProducts(1);
    });
  }

  getProducts(page: number): void {

    this.isLoading = true;
    this.productService
      .filterProductByLimitAndPage(5, page)
      .pipe(

        tap((res: ResponseResult<PageableFilter<Product[]>>) => {
          this.pageableFilter = { ...res.data };

        }),

        finalize(() => {
          this.isLoading = false;
          this.showDetailCode = '';
        })
      )
      .subscribe();
  }


  searchProduct(): void {
    this.isLoading = true;

    this.routeService.query((query: any) => {
      if (query.search) {
        this.searchValue = query.search;

        this.productService
          .searchProduct(query.search)
          .pipe(
            tap((res: ResponseResult<Product[]>) => {
              this.pageableFilter.content = res.data;
            }),

            finalize(() => this.isLoading = false)
          )
          .subscribe()
      }
    });
  }


  onClickLabelSearchTemp(): void {
    // console.log(this.search_inpEl.nativeElement);
    this.search_inpEl.nativeElement.focus();
  }

  onFocusSearch(): void {
    this.focus_Search = true;
  }

  onChangeSearchInput(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;
    this.searchValue = value;
    this.routeService.navigate('', { search: value });

    if (value == '') {
      this.routeService.navigate('', { search: null });
      const currentPage: number = this.pageableFilter.pageNumber || 1;
      this.router.navigate([`/products?page=${currentPage}`]);
    } else this.searchProduct();

  }

  onFocusOut(): void {
    if (this.searchValue !== '') return;

    this.focus_Search = false;
  }

  onShowDetail(code: string) {
    this.showDetailCode = code;
    this.isOpenDetailForm = true;
  }
}
