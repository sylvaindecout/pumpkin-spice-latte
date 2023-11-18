import { createAction, props } from '@ngrx/store';
import { CatalogueItem } from '../catalogue-item';

export const listenToCatalogue = createAction('listenToCatalogue');
export const refreshCatalogue = createAction('refreshCatalogue');
export const catalogue = createAction('catalogue', props<{ items: CatalogueItem[] }>());
