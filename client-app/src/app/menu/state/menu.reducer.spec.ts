import {MenuItem} from '../menu-item';
import {menu} from './menu.actions';
import * as fromReducer from './menu.reducer';

describe('MenuEffects', () => {

  it('should process menu action', () => {
    const item1: MenuItem = {
      name: 'Espresso',
      unitPrice: {
        amountMinor: 450,
        currencyUnit: 'EUR',
        scale: 2,
      },
    };
    const state: fromReducer.State = {
      items: [],
    };

    const updatedState = fromReducer.menuReducer(state, menu({items: [item1]}));

    expect(updatedState.items).toEqual([item1]);
  });

});
