import {createFeatureSelector, createReducer, createSelector, on} from '@ngrx/store';
import {Invoice} from '../invoice';
import {newInvoice} from './invoices.actions';

export interface State {
  invoices: Invoice[],
}

export const initialState: State = {
  invoices: [],
}

export const invoicesReducer = createReducer(
  initialState,
  on(newInvoice, (state, {invoice}) => ({
    ...state,
    invoices: [...state.invoices, invoice],
  }),)
);

const featureSelector = createFeatureSelector<State>('invoices');

export const selectInvoices = createSelector(featureSelector, state => state.invoices);
