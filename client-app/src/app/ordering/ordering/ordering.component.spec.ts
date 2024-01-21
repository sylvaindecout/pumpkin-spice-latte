import { TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { provideMockStore } from '@ngrx/store/testing';
import { InvoiceListComponent } from '../invoice-list/invoice-list.component';
import { initialState } from '../state/invoices.reducer';
import { OrderingComponent } from './ordering.component';

describe('OrderingComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      MatDialogModule,
      OrderingComponent,
      InvoiceListComponent,
    ],
    providers: [
      provideMockStore({initialState}),
    ],
  }));

  it('should create the component', () => {
    const fixture = TestBed.createComponent(OrderingComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
