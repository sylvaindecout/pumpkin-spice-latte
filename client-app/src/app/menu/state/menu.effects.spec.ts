import { Action, ActionsSubject } from '@ngrx/store';
import { Observable, of } from 'rxjs';
import { MenuItem } from '../menu-item';
import { MenuService } from '../menu.service';
import { menu, refreshMenu } from './menu.actions';
import { MenuEffects } from './menu.effects';

describe('MenuEffects', () => {

  const actions$ = new ActionsSubject();
  const orderingServiceMock = <MenuService>{
    findAllDrinks(): Observable<MenuItem[]> { return of(); }
  };
  const effects = new MenuEffects(actions$, orderingServiceMock);

  it('should call API and trigger menu action on refreshMenu action', () => {
    const item1: MenuItem = {
      name: 'Espresso',
      unitPrice: {
        amountMinor: 450,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    spyOn(orderingServiceMock, 'findAllDrinks').and.returnValue(of([item1]));
    const result: Action[] = [];
    effects.refreshMenu$.subscribe((action) => result.push(action));

    actions$.next(refreshMenu());

    expect(result).toEqual([menu({ items: [item1] })]);
  });

});
