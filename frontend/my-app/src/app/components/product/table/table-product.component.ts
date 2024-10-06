import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { PayloadTokenModel } from '../../../model/payload-token.model';
import { ProductModel } from '../../../model/product.model';
import { ProductApiService } from '../../../service/api/product-api.service';
import { JwtService } from '../../../service/utils/jwt.service';
import { RouteService } from '../../../service/utils/route.service';
import { LoadingComponent } from '../../common/loading/loading.component';
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
  // State component
  public focus_Search: boolean = false;
  public isLoading: boolean = false;

  public isOpenSaveForm: boolean = false;
  public isOpenDetailForm: boolean = false;

  public showDetailCode: string = '';

  // Binding component
  public searchValue: string = '';
  public ListProductModel: ProductModel[] = [];

  @ViewChild('searchInpEl')
  public search_inpEl!: ElementRef<HTMLInputElement>;

  public pages: number = 0;

  public currentPage: number = 0;

  public limitProductInPage: number = 5;

  public sizeProducts: number = 0;

  public payloadToken!: PayloadTokenModel;

  constructor(
    private productApiService: ProductApiService,
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
      this.getProducts(this.currentPage);
    }
  }

  fillDataByPage(res: any): void {
    // console.log('dsa');

    const results = res.responseEntity.results;

    this.ListProductModel = results.values.products;
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
    console.log('hello')

    this.isLoading = true;
    this.productApiService
      .filter(this.limitProductInPage, page)
      .subscribe((res: any) => this.fillDataByPage(res));
  }

  fillDataBySearch(res: any): void {

    // console.log(res);

    const products = res.responseEntity.products;

    this.ListProductModel = products;
    this.isLoading = false;
  }

  searchProduct(): void {
    this.isLoading = true;

    this.routeService.query((query: any) => {
      if (query.search) {
        this.searchValue = query.search;

        this.productApiService
          .search(query.search)
          .subscribe((res: any) => this.fillDataBySearch(res));
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
