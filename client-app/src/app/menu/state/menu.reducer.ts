import {createFeatureSelector, createReducer, createSelector, on} from '@ngrx/store';
import {MenuItem} from '../menu-item';
import {menu} from './menu.actions';

export interface State {
  items: MenuItem[],
}

export const initialState: State = {
  items: [],
}

export const menuReducer = createReducer(
  initialState,
  on(menu, (state, {items}) => ({
    ...state,
    items: [...items],
  }),)
);

const featureSelector = createFeatureSelector<State>('menu');

export const selectMenuItems = createSelector(featureSelector, state => state.items);
