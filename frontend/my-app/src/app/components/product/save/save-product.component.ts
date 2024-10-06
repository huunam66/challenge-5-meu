import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { ProductError } from '../../../error/config/ProductError.config';
import { ProductModel } from '../../../model/product.model';
import { ProductApiService } from '../../../service/api/product-api.service';
import { ConfirmBoxComponent } from '../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-save-product',
  standalone: true,
  imports: [MatIconModule, CommonModule, ConfirmBoxComponent],
  templateUrl: './save-product.component.html',
  styleUrl: './save-product.component.scss',
})
export class SaveProductComponent implements OnInit {
  public productModel: ProductModel = new ProductModel();
  public productModelError: ProductError = new ProductError();

  // data flow component
  public categories: string[] = [];
  public brands: string[] = [];

  public isLoading: boolean = false;

  @Input()
  public forSave!: string;

  @Input()
  public isOpenThis: boolean = false;

  @Output()
  public onCloseThis = new EventEmitter<boolean>();

  @Output()
  public onReloadWhenSaveDone = new EventEmitter();

  public isConfirmExit: boolean = false;

  public isConfirmSave: boolean = false;

  constructor(
    private toastr: ToastrService,
    private productApiService: ProductApiService
  ) {}

  ngOnInit(): void {
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

  onCancelConfirmSave(): void {
    this.isConfirmSave = false;
  }

  onCancelConfirmExit(): void {
    this.isConfirmExit = false;
  }

  onOkExitFormSave(): void {
    console.log('saveProduct:');
    this.isOpenThis = !this.isOpenThis;

    console.log(this.onCloseThis);

    setTimeout(() => {
      this.onCloseThis.emit(this.isOpenThis);
    }, 400);
  }

  onClose(): void {
    const errorName = this.productModel.name == '';
    const errorCategory = this.productModel.category == '';
    const errorBrand = this.productModel.brand == '';
    const errorType = this.productModel.type == '';
    const errorDescription = this.productModel.description == '';
    if (
      (!errorName ||
        !errorBrand ||
        !errorCategory ||
        !errorType ||
        !errorDescription) &&
      this.isOpenThis
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

    const nameErrorTemplate = this.productModelError.name;
    if (value == '' || value == null) {
      statusError = true;
    }

    this.productModelError.name = { ...nameErrorTemplate, error: statusError };
    this.productModel.name = value.trim();
  }

  onOptionCategoryChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const categoryErrorTemplate = this.productModelError.category;
    if (value == '' || value == null) {
      statusError = !statusError;
    }
    this.productModelError.category = {
      ...categoryErrorTemplate,
      error: statusError,
    };

    this.productModel.category = value.trim();
  }

  onOptionBrandChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const brandErrorTemplate = this.productModelError.brand;
    if (value == '' || value == null) {
      statusError = !statusError;
    }

    this.productModelError.brand = {
      ...brandErrorTemplate,
      error: statusError,
    };
    this.productModel.brand = value;
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.productModel.type = value;
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;

    this.productModel.description = value;
  }

  onErrorTextName(): boolean {
    return this.productModelError.name.error;
  }

  onErrorOptionCategory(): boolean {
    return this.productModelError.category.error;
  }

  onErrorOptionBrand(): boolean {
    return this.productModelError.brand.error;
  }

  onSubmitAddNew(): void {
    const errorName = this.productModel.name == '';
    const errorBrand = this.productModel.brand == '';
    const errorCategory = this.productModel.category == '';
    if (errorName || errorBrand || errorCategory) {
      const errors = [];
      const nameErrorTemplate = this.productModelError.name;
      const categoryErrorTemplate = this.productModelError.category;
      const brandErrorTemplate = this.productModelError.brand;
      if (errorName) {
        this.productModelError.name = { ...nameErrorTemplate, error: true };
        errors.push(nameErrorTemplate.message);
      }
      if (errorBrand) {
        this.productModelError.brand = { ...brandErrorTemplate, error: true };
        errors.push(brandErrorTemplate.message);
      }
      if (errorCategory) {
        this.productModelError.category = {
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
        .post(this.productModel)
        .subscribe((res) => this.onResponseSavingProduct(res), (err) => {
          this.isLoading = false;
          this.isConfirmSave = false;
          this.toastr.error(err.error.message, '', {
            closeButton: true,
            timeOut: 5000,
          });
        });
    }, 2000);
  }

  onResponseSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;

    console.log(res);
    if (res.code != 201) {
      this.toastr.error(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });
    } else {
      this.toastr.success(res.message, '', {
        closeButton: true,
        timeOut: 5000,
      });

      this.onCloseThis.emit(!this.isOpenThis);
      this.onReloadWhenSaveDone.emit();
    }
  }
}
