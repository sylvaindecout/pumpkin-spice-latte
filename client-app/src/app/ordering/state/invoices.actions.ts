import {createAction, props} from '@ngrx/store';
import {Invoice} from '../invoice';
import {Order} from '../order';

export const sendOrder = createAction('sendOrder', props<{ order: Order }>());
export const newInvoice = createAction('newInvoice', props<{ invoice: Invoice }>());
