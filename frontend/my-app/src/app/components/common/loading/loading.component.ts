import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loading',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './loading.component.html',
  styleUrl: './loading.component.scss',
})
export class LoadingComponent implements OnInit {
  @Input()
  public widthIcon!: string;

  @Input()
  public heightIcon!: string;

  public sizeIcon!: any;

  ngOnInit(): void {
    this.sizeIcon = {
      width: this.widthIcon,
      height: this.heightIcon,
    };
  }
}
