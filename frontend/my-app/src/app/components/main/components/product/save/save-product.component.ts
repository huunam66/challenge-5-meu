import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { catchError, finalize, tap, throwError } from 'rxjs';
import { ProductError } from '../../../../../../error/config/ProductError.config';
import { Product } from '../../../../../../model/product.model';
import { ResponseResult } from '../../../../../../model/responseResult.model';
import { ProductService } from '../../../../../../service/product.service';
import { ConfirmBoxComponent } from '../../../../common/confirm-box/confirm-box.component';

@Component({
  selector: 'app-save-product',
  standalone: true,
  imports: [
    MatIconModule,
    CommonModule,
    ConfirmBoxComponent,
    ReactiveFormsModule
  ],
  templateUrl: './save-product.component.html',
  styleUrl: './save-product.component.scss',
})
export class SaveProductComponent {

  @Input() forSave!: string;
  @Input() isOpenThis: boolean = false;
  @Output() onCloseThis = new EventEmitter<boolean>();
  @Output() onReloadWhenSaveDone = new EventEmitter();

  productSave: FormGroup = new FormGroup({
    id: new FormControl(''),
    code: new FormControl(''),
    name: new FormControl(''),
    category: new FormControl(''),
    brand: new FormControl(''),
    type: new FormControl(''),
    description: new FormControl('')
  });

  productError: ProductError = new ProductError();

  categories: string[] = [];
  brands: string[] = [];
  isLoading: boolean = false;
  isConfirmExit: boolean = false;
  isConfirmSave: boolean = false;


  constructor(
    private toastr: ToastrService,
    private productService: ProductService
  ) {
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
    this.isOpenThis = !this.isOpenThis;

    setTimeout(() => {
      this.onCloseThis.emit(this.isOpenThis);
    }, 400);
  }

  onClose(): void {

    const productEditing: Product = this.productSave.value;

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

    this.productError.name = {
      ...nameErrorTemplate,
      error: statusError
    };

    this.productSave.patchValue({
      name: value.trim()
    })
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

    this.productSave.patchValue({
      category: value.trim()
    })
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

    this.productSave.patchValue({
      brand: value.trim()
    })
  }

  onTextTypeChange(event: Event): void {
    const value = (<HTMLInputElement>event.target).value;

    this.productSave.patchValue({
      type: value.trim()
    })
  }

  onTextDescriptionChange(event: Event): void {
    const value = (<HTMLTextAreaElement>event.target).value;


    this.productSave.patchValue({
      description: value.trim()
    })
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
    const { name, brand, category } = this.productSave.value;

    const errorName = name == '';
    const errorBrand = brand == '';
    const errorCategory = category == '';
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

    this.isConfirmSave = true;
  }

  onSavingProduct(): void {
    this.isLoading = true;
    setTimeout(() => {
      const productToSave: Product = this.productSave.value;

      this.productService
        .postProduct(productToSave)
        .pipe(

          tap((res: ResponseResult<Product>) => {
            this.toastr.success(res.message, '');

            this.onCloseThis.emit(!this.isOpenThis);
            this.onReloadWhenSaveDone.emit();
          }),

          catchError((error: ResponseResult<never>) => {
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
}
