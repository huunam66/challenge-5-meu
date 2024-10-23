import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { ProductApiService } from '../../../../../../service/product/product.service';
import { JwtService } from '../../../../../../utils/jwt.service';
import { ProductError } from '../../../../../error/config/ProductError.config';
import { PayloadToken } from '../../../../../model/payload-token.model';
import { Product } from '../../../../../model/product.model';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [MatIconModule, CommonModule, FormsModule, ConfirmBoxComponent],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.scss',
})
export class DetailProductComponent implements OnInit, OnDestroy {
  productDetail!: Product;
  productEdit!: Product;
  productEditError: ProductError = new ProductError();

  // data flow component
  categories: string[] = [];
  brands: string[] = [];

  isLoading: boolean;

  @Input()
  detailCode!: string;

  @Input()
  isDetailView!: boolean;

  @Input()
  isEditView!: boolean;

  @Input()
  isOpenThis: boolean;

  @Output()
  onCloseThis = new EventEmitter<any>();

  isConfirmExit: boolean = false;

  isConfirmSave: boolean = false;

  isConfirmDelete: boolean = false;

  payloadToken!: PayloadToken

  private subcription: Subscription;

  constructor(
    private toastr: ToastrService,
    private productApiService: ProductApiService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }


  ngOnInit(): void {
    this.subcription = this.productApiService
      .get(this.detailCode)
      .pipe(
        finalize(() => {
          this.subcription.unsubscribe();
        })
      )
      .subscribe((res: any) => {
        console.log(res);
        this.productEdit = this.productDetail = res.data.product;
      });

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

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
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

  onDeleteProduct(): void {
    this.isLoading = true;

    setTimeout(() => {
      this.subcription = this.productApiService
        .delete(this.productDetail.code)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedDeleteProduct(res),
          error: (err) => console.log(err)
        })
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
    // console.log('saveProduct:');

    this.isOpenThis = !this.isOpenThis;

    // console.log(this.onCloseThis);

    setTimeout(() => {
      this.onCloseThis.emit({
        statusIsOpen: this.isOpenThis,
        statusReloadTable: false,
      });
    }, 400);
  }

  onClose(): void {
    const errorName = this.productEdit.name == '';
    const errorCategory = this.productEdit.category == '';
    const errorBrand = this.productEdit.brand == '';
    const errorType = this.productEdit.type == '';
    const errorDescription = this.productEdit.description == '';
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
    if (value == '' || value == null) {
      statusError = true;
    }

    this.productEditError.name = {
      ...nameErrorTemplate,
      error: statusError,
    };
    this.productEdit.name = value.trim();
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

    this.productEdit.category = value.trim();
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
    this.productEdit.brand = value;
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.productEdit.type = value;
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;

    this.productEdit.description = value;
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
    const errorName = this.productEdit.name == '';
    const errorBrand = this.productEdit.brand == '';
    const errorCategory = this.productEdit.category == '';
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

  onSavingProduct(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.subcription = this.productApiService
        .put(this.productEdit, this.productEdit.code)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res: any) => this.onResponsedSavingProduct(res),
          error: (err) => {
            this.isLoading = false;
            this.isConfirmSave = false;
            this.toastr.error(err.error.message, '');
          }
        })
    }, 2000);
  }

  onResponsedSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;
    if (res.code != 201) {
      this.toastr.error('Lỗi lưu sản phẩm!', '');
    } else {
      this.toastr.success(res.message, '');

      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
    }
  }
}
