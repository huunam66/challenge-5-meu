import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { ProductError } from '../../../error/config/ProductError.config';
import { PayloadTokenModel } from '../../../model/payload-token.model';
import { ProductModel } from '../../../model/product.model';
import { ProductApiService } from '../../../service/api/product-api.service';
import { JwtService } from '../../../service/utils/jwt.service';
import { ConfirmBoxComponent } from '../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [MatIconModule, CommonModule, FormsModule, ConfirmBoxComponent],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.scss',
})
export class DetailProductComponent implements OnInit {
  public productModelDetail!: ProductModel;
  public productModelEdit!: ProductModel;
  public productModelEditError: ProductError = new ProductError();

  // data flow component
  public categories: string[] = [];
  public brands: string[] = [];

  public isLoading: boolean;

  @Input()
  public detailCode!: string;

  @Input()
  public isDetailView!: boolean;

  @Input()
  public isEditView!: boolean;

  @Input()
  public isOpenThis: boolean;

  @Output()
  public onCloseThis = new EventEmitter<any>();

  public isConfirmExit: boolean = false;

  public isConfirmSave: boolean = false;

  public isConfirmDelete: boolean = false;

  public payloadToken!: PayloadTokenModel

  constructor(
    private toastr: ToastrService,
    private productApiService: ProductApiService,
    private jwtService: JwtService
  ) {
    this.payloadToken = jwtService.getPayload();
  }

  ngOnInit(): void {
    this.productApiService.get(this.detailCode).subscribe((res: any) => {
      this.productModelEdit = this.productModelDetail = res.responseEntity.product;
      // console.log(this.productModelDetail);
      // console.log(this.productModelEdit);
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
      this.productApiService
        .delete(this.productModelDetail.code)
        .subscribe((res: any) => this.onResponsedDeleteProduct(res));
    }, 2000);
  }

  onResponsedDeleteProduct(res: any): void {
    // console.log(res);
    this.isLoading = false;
    this.isConfirmDelete = false;
    if (res.code != 200) {
      this.toastr.error(res.message, 'Lỗi xóa sản phẩm!', {
        timeOut: 5000,
        closeButton: true,
      });
    } else {
      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
      this.toastr.success(res.message, '', {
        timeOut: 5000,
        closeButton: true,
      });
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
    const errorName = this.productModelEdit.name == '';
    const errorCategory = this.productModelEdit.category == '';
    const errorBrand = this.productModelEdit.brand == '';
    const errorType = this.productModelEdit.type == '';
    const errorDescription = this.productModelEdit.description == '';
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

    const nameErrorTemplate = this.productModelEditError.name;
    if (value == '' || value == null) {
      statusError = true;
    }

    this.productModelEditError.name = {
      ...nameErrorTemplate,
      error: statusError,
    };
    this.productModelEdit.name = value.trim();
  }

  onOptionCategoryChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const categoryErrorTemplate = this.productModelEditError.category;
    if (value == '' || value == null) {
      statusError = !statusError;
    }
    this.productModelEditError.category = {
      ...categoryErrorTemplate,
      error: statusError,
    };

    this.productModelEdit.category = value.trim();
  }

  onOptionBrandChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const brandErrorTemplate = this.productModelEditError.brand;
    if (value == '' || value == null) {
      statusError = !statusError;
    }

    this.productModelEditError.brand = {
      ...brandErrorTemplate,
      error: statusError,
    };
    this.productModelEdit.brand = value;
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.productModelEdit.type = value;
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;

    this.productModelEdit.description = value;
  }

  onErrorTextName(): boolean {
    return this.productModelEditError.name.error;
  }

  onErrorOptionCategory(): boolean {
    return this.productModelEditError.category.error;
  }

  onErrorOptionBrand(): boolean {
    return this.productModelEditError.brand.error;
  }

  onSubmitAddNew(): void {
    const errorName = this.productModelEdit.name == '';
    const errorBrand = this.productModelEdit.brand == '';
    const errorCategory = this.productModelEdit.category == '';
    if (errorName || errorBrand || errorCategory) {
      const errors = [];
      const nameErrorTemplate = this.productModelEditError.name;
      const categoryErrorTemplate = this.productModelEditError.category;
      const brandErrorTemplate = this.productModelEditError.brand;
      if (errorName) {
        this.productModelEditError.name = { ...nameErrorTemplate, error: true };
        errors.push(nameErrorTemplate.message);
      }
      if (errorBrand) {
        this.productModelEditError.brand = {
          ...brandErrorTemplate,
          error: true,
        };
        errors.push(brandErrorTemplate.message);
      }
      if (errorCategory) {
        this.productModelEditError.category = {
          ...categoryErrorTemplate,
          error: true,
        };
        errors.push(categoryErrorTemplate.message);
      }

      errors.forEach((error, index) => {
        setTimeout(() => {
          this.toastr.error(error, 'Lỗi ràng buộc!', {
            closeButton: true,
            timeOut: 5000,
          });
        }, 600 * (index + 1));
      });

      return;
    }

    // console.log(this.productModel);

    this.isConfirmSave = true;
  }

  onSavingProduct(): void {
    this.isLoading = true;
    setTimeout(() => {
      this.productApiService
        .put(this.productModelEdit, this.productModelEdit.code)
        .subscribe((res: any) => this.onResponsedSavingProduct(res), (err) => {
          this.isLoading = false;
          this.isConfirmSave = false;
          this.toastr.error(err.error.message, '', {
            closeButton: true,
            timeOut: 5000,
          });
        });
    }, 2000);
  }

  onResponsedSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;
    if (res.code != 201) {
      this.toastr.error('Lỗi lưu sản phẩm!', '', {
        closeButton: true,
        timeOut: 5000,
      });
    } else {
      this.toastr.success(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });

      this.onCloseThis.emit({
        statusClose: !this.isOpenThis,
        statusReloadTable: true,
      });
    }
  }
}
