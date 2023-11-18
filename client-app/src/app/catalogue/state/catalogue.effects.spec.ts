import { Action, ActionsSubject } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { CatalogueItem } from '../catalogue-item';
import { CatalogueService } from '../catalogue.service';
import { catalogue, refreshCatalogue } from './catalogue.actions';
import { CatalogueEffects } from './catalogue.effects';

describe('CatalogueEffects', () => {

  const actions$ = new ActionsSubject();
  const orderingServiceMock = <CatalogueService>{
    findAllDrinks(): Observable<CatalogueItem[]> { return of(); }
  };
  const effects = new CatalogueEffects(actions$, orderingServiceMock);

  it('should call API and trigger catalogue action on refreshCatalogue action', () => {
    const item1: CatalogueItem = {
      name: 'Espresso',
      unitPrice: {
        amountMinor: 450,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    spyOn(orderingServiceMock, 'findAllDrinks').and.returnValue(of([item1]));
    const result: Action[] = [];
    effects.refreshCatalogue$.subscribe((action) => result.push(action));

    actions$.next(refreshCatalogue());

    expect(result).toEqual([catalogue({ items: [item1] })]);
  });

});
