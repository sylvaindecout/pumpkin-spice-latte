import {Action, ActionsSubject} from '@ngrx/store';
import {Observable, of} from 'rxjs';
import {Invoice} from '../invoice';
import {Order} from '../order';
import {OrderingService} from '../ordering.service';
import {newInvoice, sendOrder} from './invoices.actions';
import {InvoicesEffects} from './invoices.effects';

describe('InvoicesEffects', () => {

  const actions$ = new ActionsSubject();
  const orderingServiceMock = <OrderingService>{
    process(order: Order): Observable<Invoice> {
      return of();
    }
  };
  const effects = new InvoicesEffects(actions$, orderingServiceMock);

  it('should call API and trigger newInvoice action on sendOrder action', () => {
    const order: Order = {
      drink: 'Latte',
      quantity: 2,
      customer: 'Giorgio',
    }
    const invoice: Invoice = {
      drink: 'Latte',
      quantity: 2,
      customer: 'Giorgio',
      totalPrice: {
        amountMinor: 750,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    spyOn(orderingServiceMock, 'process').withArgs(order).and.returnValue(of(invoice));
    const result: Action[] = [];
    effects.sendOrder$.subscribe((action) => result.push(action));

    actions$.next(sendOrder({order}));

    expect(result).toEqual([newInvoice({invoice})]);
  });

});
