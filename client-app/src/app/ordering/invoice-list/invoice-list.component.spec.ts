import { TestBed } from '@angular/core/testing';
import { provideMockStore } from "@ngrx/store/testing";
import { initialState } from '../state/invoices.reducer';
import { InvoiceListComponent } from './invoice-list.component';

describe('InvoiceListComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [InvoiceListComponent],
    providers: [
      provideMockStore({initialState})
    ],
  }));

  it('should create the component', () => {
    const fixture = TestBed.createComponent(InvoiceListComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
