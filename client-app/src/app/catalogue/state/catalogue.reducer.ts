import { createFeatureSelector, createReducer, createSelector, on } from '@ngrx/store';
import { CatalogueItem } from '../catalogue-item';
import { catalogue } from './catalogue.actions';

export interface State {
  items: CatalogueItem[],
}

export const initialState: State = {
  items: [],
}

export const catalogueReducer = createReducer(
  initialState,
  on(catalogue, (state, {items}) => ({
    ...state,
    items: [ ...items ],
  }),)
);

const featureSelector = createFeatureSelector<State>('catalogue');

export const selectCatalogueItems = createSelector(featureSelector, state => state.items);
