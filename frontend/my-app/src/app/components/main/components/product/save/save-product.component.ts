import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { finalize, Subscription } from 'rxjs';
import { ProductApiService } from '../../../../../../service/product/product.service';
import { ProductError } from '../../../../../error/config/ProductError.config';
import { Product } from '../../../../../model/product.model';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-save-product',
  standalone: true,
  imports: [MatIconModule, CommonModule, ConfirmBoxComponent],
  templateUrl: './save-product.component.html',
  styleUrl: './save-product.component.scss',
})
export class SaveProductComponent implements OnInit, OnDestroy {
  product: Product = new Product();
  productError: ProductError = new ProductError();

  // data flow component
  categories: string[] = [];
  brands: string[] = [];

  isLoading: boolean = false;

  @Input()
  forSave!: string;

  @Input()
  isOpenThis: boolean = false;

  @Output()
  onCloseThis = new EventEmitter<boolean>();

  @Output()
  onReloadWhenSaveDone = new EventEmitter();

  isConfirmExit: boolean = false;

  isConfirmSave: boolean = false;


  private subcription: Subscription;

  constructor(
    private toastr: ToastrService,
    private productApiService: ProductApiService
  ) { }

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

  ngOnDestroy(): void {
    this.subcription?.unsubscribe();
  }

  onCancelConfirmSave(): void {
    this.isConfirmSave = false;
  }

  onCancelConfirmExit(): void {
    this.isConfirmExit = false;
  }

  onOkExitFormSave(): void {
    this.isOpenThis = !this.isOpenThis;

    setTimeout(() => {
      this.onCloseThis.emit(this.isOpenThis);
    }, 400);
  }

  onClose(): void {
    const errorName = this.product.name == '';
    const errorCategory = this.product.category == '';
    const errorBrand = this.product.brand == '';
    const errorType = this.product.type == '';
    const errorDescription = this.product.description == '';
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

    const nameErrorTemplate = this.productError.name;
    if (value == '' || value == null) {
      statusError = true;
    }

    this.productError.name = { ...nameErrorTemplate, error: statusError };
    this.product.name = value.trim();
  }

  onOptionCategoryChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const categoryErrorTemplate = this.productError.category;
    if (value == '' || value == null) {
      statusError = !statusError;
    }
    this.productError.category = {
      ...categoryErrorTemplate,
      error: statusError,
    };

    this.product.category = value.trim();
  }

  onOptionBrandChange(event: Event): void {
    const value = (<HTMLSelectElement>event.target).value;

    let statusError: boolean = false;

    const brandErrorTemplate = this.productError.brand;
    if (value == '' || value == null) {
      statusError = !statusError;
    }

    this.productError.brand = {
      ...brandErrorTemplate,
      error: statusError,
    };
    this.product.brand = value;
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.product.type = value;
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;

    this.product.description = value;
  }

  onErrorTextName(): boolean {
    return this.productError.name.error;
  }

  onErrorOptionCategory(): boolean {
    return this.productError.category.error;
  }

  onErrorOptionBrand(): boolean {
    return this.productError.brand.error;
  }

  onSubmitAddNew(): void {
    const errorName = this.product.name == '';
    const errorBrand = this.product.brand == '';
    const errorCategory = this.product.category == '';
    if (errorName || errorBrand || errorCategory) {
      const errors = [];
      const nameErrorTemplate = this.productError.name;
      const categoryErrorTemplate = this.productError.category;
      const brandErrorTemplate = this.productError.brand;
      if (errorName) {
        this.productError.name = { ...nameErrorTemplate, error: true };
        errors.push(nameErrorTemplate.message);
      }
      if (errorBrand) {
        this.productError.brand = { ...brandErrorTemplate, error: true };
        errors.push(brandErrorTemplate.message);
      }
      if (errorCategory) {
        this.productError.category = {
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
        .post(this.product)
        .pipe(
          finalize(() => this.subcription.unsubscribe())
        )
        .subscribe({
          next: (res) => this.onResponseSavingProduct(res),
          error: (err) => {
            this.isLoading = false;
            this.isConfirmSave = false;
            this.toastr.error(err.error.message, '');
          }
        })
    }, 2000);
  }

  onResponseSavingProduct(res: any): void {
    this.isLoading = false;
    this.isConfirmSave = false;

    console.log(res);
    if (res.code != 201) {
      this.toastr.error(res.message, '');
    } else {
      this.toastr.success(res.message, '');

      this.onCloseThis.emit(!this.isOpenThis);
      this.onReloadWhenSaveDone.emit();
    }
  }
}
