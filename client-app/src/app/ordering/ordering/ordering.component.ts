import {Component} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewOrderDialogComponent} from '../new-order-dialog/new-order-dialog.component';

@Component({
  selector: 'ordering',
  templateUrl: './ordering.component.html',
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
