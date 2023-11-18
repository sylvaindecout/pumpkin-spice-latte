import { CatalogueItem } from '../catalogue-item';
import { catalogue } from './catalogue.actions';
import * as fromReducer from './catalogue.reducer';

describe('CatalogueEffects', () => {

  it('should process catalogue action', () => {
    const item1: CatalogueItem = {
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

    const updatedState = fromReducer.catalogueReducer(state, catalogue({items: [item1]}));

    expect(updatedState.items).toEqual([item1]);
  });

});
