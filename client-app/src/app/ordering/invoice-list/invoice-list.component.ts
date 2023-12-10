import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Invoice} from '../invoice';
import {selectInvoices} from '../state/invoices.reducer';
import {MoneyPipe} from '../../shared/money.pipe';
import {AsyncPipe, NgFor, NgIf} from '@angular/common';

@Component({
  selector: 'invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrl: './invoice-list.component.scss',
  standalone: true,
  imports: [NgIf, NgFor, AsyncPipe, MoneyPipe],
})
export class InvoiceListComponent {

  readonly invoices$: Observable<Invoice[]>;

  constructor(
    private readonly store: Store,
  ) {
    this.invoices$ = store.select(selectInvoices).pipe(map(invoices => [...invoices].reverse()));
  }

  get noInvoice$(): Observable<boolean> {
    return this.invoices$.pipe(map(list => list.length < 1));
  }

}
