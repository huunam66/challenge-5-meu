import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, finalize, tap, throwError } from 'rxjs';
import { ProductError } from '../../../../../../error/config/ProductError.config';
import { PayloadToken } from '../../../../../../model/payload-token.model';
import { Product } from '../../../../../../model/product.model';
import { ResponseResult } from '../../../../../../model/responseResult.model';
import { ProductService } from '../../../../../../service/product.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [
    MatIconModule,
    CommonModule,
    FormsModule,
    ConfirmBoxComponent,
    ReactiveFormsModule
  ],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.scss',
})
export class DetailProductComponent implements OnInit {

  @Input() detailCode: string;
  @Input() isDetailView: boolean;
  @Input() isEditView: boolean;
  @Input() isOpenThis: boolean;
  @Output() onCloseThis = new EventEmitter<any>();

  payloadToken: PayloadToken
  productDetail: Product;
  productEditError: ProductError = new ProductError();

  productEdit: FormGroup = new FormGroup({
    id: new FormControl(''),
    code: new FormControl(''),
    name: new FormControl(''),
    category: new FormControl(''),
    brand: new FormControl(''),
    type: new FormControl(''),
    description: new FormControl('')
  })

  categories: string[] = [];
  brands: string[] = [];
  isLoading: boolean;
  isConfirmExit: boolean = false;
  isConfirmSave: boolean = false;
  isConfirmDelete: boolean = false;

  constructor(
    private toastr: ToastrService,
    private productService: ProductService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();

    this.categories = [
      'Bedding',
      'Hair Styling Tools',
      'Health Accessories',
      'Hijab',
      'Kids Health & Skincare',
      "Men's Clothing",
      'Mobile & Gadgets',
      'Small Kitchen Appliances',
      'Snacks',
    ];

    this.brands = [
      'No Brand',
      'Naelofar',
      'Philips',
      'Gemei',
      'Oreo',
      'Samsung',
      'Realme',
      'Nokia',
      'Akemi',
      'OEM',
      'Medicos',
    ];
  }


  ngOnInit(): void {
    this.getProduct();
  }

  getProduct() {
    this.productService
      .getProductByCode(this.detailCode)
      .pipe(

        tap((res: ResponseResult<Product>) => {
          this.productDetail = res.data || {} as Product;
        }),

        catchError((error: ResponseResult<never>) => {
          console.log(error.message);
          return throwError(() => error);
        })

      )
      .subscribe();
  }


  onTurnEditView(): void {
    this.isDetailView = false;
    this.isEditView = true;
  }

  backToDetailView(): void {
    this.isEditView = false;
    this.isDetailView = true;
  }

  onCancelConfirmSave(): void {
    this.isConfirmSave = false;
  }

  onCancelConfirmExit(): void {
    this.isConfirmExit = false;
  }

  onConfirmDelete(): void {
    this.isConfirmDelete = true;
  }

  onCancelConfirmDelete(): void {
    this.isConfirmDelete = false;
  }

  onSavingProduct(): void {
    this.isLoading = true;
    setTimeout(() => {
      const productEditing: Product = this.productEdit.value;

      this.productService
        .putProduct(productEditing, this.productDetail.code || '')
        .pipe(

          tap((res: ResponseResult<Product>) => {
            this.toastr.success(res.message, '');

            this.onCloseThis.emit({
              statusClose: !this.isOpenThis,
              statusReloadTable: true,
            });
          }),

          catchError((error) => {
            this.toastr.error(error.message, '');
            return throwError(() => error);
          }),

          finalize(() => {
            this.isLoading = false;
            this.isConfirmSave = false;
          })
        )
        .subscribe()
    }, 2000);
  }

  onDeleteProduct(): void {
    this.isLoading = true;

    setTimeout(() => {
      this.productService
        .deleteProduct(this.productDetail.code || '')
        .pipe(
          tap((res: ResponseResult<never>) => {
            this.onCloseThis.emit({
              statusClose: !this.isOpenThis,
              statusReloadTable: true,
            });
            this.toastr.success(res.message, '');
          }),

          catchError((error: ResponseResult<never>) => {
            this.toastr.error(error.message);
            return throwError(() => error);
          }),

          finalize(() => {
            this.isLoading = false;
            this.isConfirmDelete = false;
          })
        )
        .subscribe()
    }, 2000);
  }

  onResponsedDeleteProduct(res: any): void {
    // console.log(res);
    this.isLoading = false;
    this.isConfirmDelete = false;
    if (res.code != 200) {
      this.toastr.error(res.message, 'Lỗi xóa sản phẩm!');
    } else {
      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
      this.toastr.success(res.message, '');
    }
  }

  onOkExitFormSave(): void {

    this.isOpenThis = !this.isOpenThis;

    setTimeout(() => {
      this.onCloseThis.emit({
        statusIsOpen: this.isOpenThis,
        statusReloadTable: false,
      });
    }, 400);
  }

  onClose(): void {
    const productEditing: Product = this.productEdit.value;

    const errorName = productEditing.name == '';
    const errorCategory = productEditing.category == '';
    const errorBrand = productEditing.brand == '';
    const errorType = productEditing.type == '';
    const errorDescription = productEditing.description == '';
    if (
      (!errorName ||
        !errorBrand ||
        !errorCategory ||
        !errorType ||
        !errorDescription) &&
      this.isOpenThis &&
      this.isEditView
    ) {
      this.isConfirmExit = true;
      return;
    }

    this.onOkExitFormSave();
  }

  onCloseConfirmExit(event: boolean): void {
    this.isConfirmExit = event;
  }

  onTextNameChange(event: Event) {
    const value = (<HTMLInputElement>event.target).value;

    let statusError: boolean = false;

    const nameErrorTemplate = this.productEditError.name;
    if (!value) statusError = true;


    this.productEditError.name = {
      ...nameErrorTemplate,
      error: statusError,
    };

    this.productEdit.patchValue({
      name: value.trim()
    })
  }

  onOptionCategoryChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const categoryErrorTemplate = this.productEditError.category;
    if (value == '' || value == null) {
      statusError = !statusError;
    }
    this.productEditError.category = {
      ...categoryErrorTemplate,
      error: statusError,
    };

    this.productEdit.patchValue({
      category: value.trim()
    })
  }

  onOptionBrandChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const brandErrorTemplate = this.productEditError.brand;
    if (value == '' || value == null) {
      statusError = !statusError;
    }

    this.productEditError.brand = {
      ...brandErrorTemplate,
      error: statusError,
    };

    this.productEdit.patchValue({
      brand: value.trim()
    })
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.productEdit.patchValue({
      type: value.trim()
    })
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;

    this.productEdit.patchValue({
      description: value.trim()
    })
  }

  onErrorTextName(): boolean {
    return this.productEditError.name.error;
  }

  onErrorOptionCategory(): boolean {
    return this.productEditError.category.error;
  }

  onErrorOptionBrand(): boolean {
    return this.productEditError.brand.error;
  }

  onSubmitAddNew(): void {
    const { name, brand, category }: Product = this.productEdit.value;

    const errorName = name == '';
    const errorBrand = brand == '';
    const errorCategory = category == '';
    if (errorName || errorBrand || errorCategory) {
      const errors = [];
      const nameErrorTemplate = this.productEditError.name;
      const categoryErrorTemplate = this.productEditError.category;
      const brandErrorTemplate = this.productEditError.brand;
      if (errorName) {
        this.productEditError.name = { ...nameErrorTemplate, error: true };
        errors.push(nameErrorTemplate.message);
      }
      if (errorBrand) {
        this.productEditError.brand = {
          ...brandErrorTemplate,
          error: true,
        };
        errors.push(brandErrorTemplate.message);
      }
      if (errorCategory) {
        this.productEditError.category = {
          ...categoryErrorTemplate,
          error: true,
        };
        errors.push(categoryErrorTemplate.message);
      }

      errors.forEach((error, index) => {
        setTimeout(() => {
          this.toastr.error(error, 'Lỗi ràng buộc!');
        }, 600 * (index + 1));
      });

      return;
    }

    // console.log(this.product);

    this.isConfirmSave = true;
  }


}
