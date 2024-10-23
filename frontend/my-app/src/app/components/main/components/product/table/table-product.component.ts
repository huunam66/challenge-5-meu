import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { finalize, Subscription } from 'rxjs';
import { ProductApiService } from '../../../../../../service/product/product.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { RouteService } from '../../../../../../utils/route.service';
import { PayloadToken } from '../../../../../model/payload-token.model';
import { Product } from '../../../../../model/product.model';
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
export class TableProductComponent implements OnInit, OnDestroy {
  // State component
  focus_Search: boolean = false;
  isLoading: boolean = false;

  isOpenSaveForm: boolean = false;
  isOpenDetailForm: boolean = false;

  showDetailCode: string = '';

  // Binding component
  searchValue: string = '';
  ListProduct: Product[] = [];

  @ViewChild('searchInpEl')
  search_inpEl!: ElementRef<HTMLInputElement>;

  pages: number = 0;

  currentPage: number = 0;

  limitProductInPage: number = 5;

  sizeProducts: number = 0;

  payloadToken!: PayloadToken;

  constructor(
    private productApiService: ProductApiService,
    private routeService: RouteService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
    console.log(this.payloadToken);
  }

  private subcription: Subscription;

  ngOnInit(): void {
    this.routeService.query((query: any) => {
      if (query.search) {
        this.searchValue = query.search;
        this.focus_Search = true;
        this.searchProduct();
      } else this.getProductOnInitComponent();
    });
  }

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }

  onCloseSaveForm(event: boolean): void {
    this.isOpenSaveForm = event;
  }

  onCloseDetailForm(event: any): void {
    this.isOpenDetailForm = event.statusIsOpen;
    if (event.statusReloadTable) {
      this.getProducts(this.currentPage);
    }
  }

  fillDataByPage(res: any): void {

    const results = res.data.results;

    this.ListProduct = results.values.products;
    this.limitProductInPage = results.limit;
    this.currentPage = results.page;
    this.pages = results.pages;
    this.sizeProducts = results.size;
    this.isLoading = false;
    this.showDetailCode = '';
  }

  onPrevPage(): void {
    if (this.currentPage <= 1) return;
    this.currentPage -= 1;
    this.navigatePage();
  }

  onNextPage(): void {
    if (this.currentPage >= this.pages) return;
    this.currentPage += 1;
    this.navigatePage();
  }

  onHeadPage(): void {
    if (this.currentPage <= 1) return;
    this.currentPage = 1;
    this.navigatePage();
  }

  onTailPage(): void {
    if (this.currentPage >= this.pages) return;
    this.routeService.navigate('', { page: this.pages });
  }

  navigatePage(): void {
    this.routeService.navigate('', { page: this.currentPage });
  }

  getProductOnInitComponent(): void {
    this.routeService.query((query: any) => {
      if (query.page) {
        this.getProducts(Number.parseInt(query.page));
      } else if (this.currentPage == 0) {
        this.getProducts(1);
      }
    });
  }

  getProducts(page: number): void {

    this.isLoading = true;
    this.subcription = this.productApiService
      .filter(this.limitProductInPage, page)
      .pipe(
        finalize(() => this.subcription.unsubscribe())
      )
      .subscribe({
        next: (res: any) => this.fillDataByPage(res),
        error: (err) => console.log(err)
      })
  }

  fillDataBySearch(res: any): void {

    // console.log(res);

    const products = res.data.products;

    this.ListProduct = products;
    this.isLoading = false;
  }

  searchProduct(): void {
    this.isLoading = true;

    this.routeService.query((query: any) => {
      if (query.search) {
        this.searchValue = query.search;

        this.subcription = this.productApiService
          .search(query.search)
          .pipe(
            finalize(() => this.subcription.unsubscribe())
          )
          .subscribe({
            next: (res: any) => this.fillDataBySearch(res),
            error: (err) => console.log(err)
          })
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
      this.navigatePage();
    } else this.searchProduct();

    //this.route.queryParams.subscribe((query) => console.log(query.search))
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
