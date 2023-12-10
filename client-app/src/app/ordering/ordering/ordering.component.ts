import {Component} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewOrderDialogComponent} from '../new-order-dialog/new-order-dialog.component';
import {InvoiceListComponent} from '../invoice-list/invoice-list.component';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'ordering',
  templateUrl: './ordering.component.html',
  standalone: true,
  imports: [MatButtonModule, InvoiceListComponent],
})
export class OrderingComponent {

  constructor(
    private readonly dialog: MatDialog,
  ) {
  }

  newOrder() {
    const dialogConfig = {
      disableClose: true,
      autoFocus: true,
      width: '500px',
      maxHeight: '90vh',
    };
    this.dialog.open(NewOrderDialogComponent, dialogConfig);
  }

}
