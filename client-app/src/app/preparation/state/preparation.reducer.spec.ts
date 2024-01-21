import {drinksInPreparation} from './preparation.actions';
import * as fromReducer from './preparation.reducer';

describe('PreparationReducer', () => {

  it('should process drinksInPreparation action', () => {
    const drink = {
      name: 'Latte',
      customer: 'Giorgio',
    };
    const state: fromReducer.State = {drinks: []};

    const updatedState = fromReducer.preparationReducer(state, drinksInPreparation({drinks: [drink]}));

    expect(updatedState.drinks).toEqual([drink]);
  });

});
