import {createFeatureSelector, createReducer, createSelector, on} from '@ngrx/store';
import {DrinkInPreparation} from '../drink-in-preparation';
import {drinksInPreparation} from './preparation.actions';

export interface State {
  drinks: DrinkInPreparation[],
}

export const initialState: State = {
  drinks: [],
}

export const preparationReducer = createReducer(
  initialState,
  on(drinksInPreparation, (state, {drinks}) => ({
    ...state,
    drinks: drinks,
  }),)
);

const featureSelector = createFeatureSelector<State>('preparation');

export const selectDrinksInPreparation = createSelector(featureSelector, state => state.drinks);
