import { Invoice } from '../invoice';
import { newInvoice } from './invoices.actions';
import * as fromReducer from './invoices.reducer';

describe('InvoicesReducer', () => {

  it('should process newInvoice action', () => {
    const invoice1: Invoice = {
      drink: 'Espresso',
      quantity: 1,
      customer: 'Giorgio',
      totalPrice: {
        amountMinor: 450,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    const invoice2: Invoice = {
      drink: 'Latte',
      quantity: 2,
      customer: 'Giorgio',
      totalPrice: {
        amountMinor: 750,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    const state: fromReducer.State = {
      invoices: [invoice1],
    };

    const updatedState = fromReducer.invoicesReducer(state, newInvoice({invoice: invoice2}));

    expect(updatedState.invoices).toEqual([invoice1, invoice2]);
  });

});
