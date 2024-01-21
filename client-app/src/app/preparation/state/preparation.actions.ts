import { createAction, props } from '@ngrx/store';
import { DrinkInPreparation } from '../drink-in-preparation';

export const listenToDrinksInPreparation = createAction('listenToDrinksInPreparation');
export const refreshDrinksInPreparation = createAction('refreshDrinksInPreparation');
export const drinksInPreparation = createAction('drinksInPreparation', props<{ drinks: DrinkInPreparation[] }>());
