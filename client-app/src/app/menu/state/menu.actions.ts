import {createAction, props} from '@ngrx/store';
import {MenuItem} from '../menu-item';

export const listenToMenu = createAction('listenToMenu');
export const refreshMenu = createAction('refreshMenu');
export const menu = createAction('menu', props<{ items: MenuItem[] }>());
