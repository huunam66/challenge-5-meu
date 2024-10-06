import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { LoadingComponent } from "../loading/loading.component";

@Component({
  selector: 'app-confirm-box',
  standalone: true,
  imports: [MatIconModule, CommonModule, LoadingComponent],
  templateUrl: './confirm-box.component.html',
  styleUrl: './confirm-box.component.scss',
})
export class ConfirmBoxComponent implements OnInit {
  @Input()
  public isOpenConfirmBox: boolean = false;

  @Input()
  public typeConfirm!: string;

  @Input()
  public messageContent!: string;

  @Input()
  public ok!: string;

  @Input()
  public cancel!: string;

  @Output()
  public okClick = new EventEmitter<any>();

  @Output()
  public cancelClick = new EventEmitter<any>();

  @Input()
  public isLoading!: boolean;

  ngOnInit(): void {
    this.typeConfirm = this.typeConfirm.toLowerCase();
  }

  onCancel() {
    this.cancelClick.emit(!this.isOpenConfirmBox);
  }

  onEventOk() {
    this.okClick.emit();
  }
}
