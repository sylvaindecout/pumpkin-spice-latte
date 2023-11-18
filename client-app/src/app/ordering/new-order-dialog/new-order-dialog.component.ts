import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { CatalogueItem } from 'src/app/catalogue/catalogue-item';
import { selectCatalogueItems } from 'src/app/catalogue/state/catalogue.reducer';
import { Order } from '../order';
import { sendOrder } from '../state/invoices.actions';

@Component({
  selector: 'new-order-dialog',
  templateUrl: './new-order-dialog.component.html',
  styleUrls: ['./new-order-dialog.component.scss']
})
export class NewOrderDialogComponent {

  readonly orderForm: FormGroup;
  readonly drinks$: Observable<CatalogueItem[]>;

  constructor(
    formBuilder: FormBuilder,
    private readonly store: Store,
    private readonly dialogRef: MatDialogRef<NewOrderDialogComponent>,
  ) {
    this.drinks$ = store.select(selectCatalogueItems);
    const customer = localStorage.getItem('customer');
    this.orderForm = formBuilder.group({
        customer: [customer, Validators.required],
        drink: [null, Validators.required],
        quantity: [1, [Validators.required, Validators.min(1)]],
    });
  }

  cancel() {
    this.dialogRef.close(false);
  }

  submit() {
    localStorage.setItem('customer', this.customerField.value);
    const order: Order = {
      drink: this.drinkField.value,
      quantity: this.quantityField.value,
      customer: this.customerField.value,
    };
    this.store.dispatch(sendOrder({ order }));
    this.dialogRef.close(true);
  }

  get customerField(): FormControl {
    return this.orderForm.get('customer') as FormControl;
  }

  get drinkField(): FormControl {
    return this.orderForm.get('drink') as FormControl;
  }

  private get quantityField(): FormControl {
    return this.orderForm.get('quantity') as FormControl;
  }
}
