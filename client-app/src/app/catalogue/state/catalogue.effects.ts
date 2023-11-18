import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, mergeMap, timer } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CatalogueService } from '../catalogue.service';
import * as actions from './catalogue.actions';

@Injectable()
export class CatalogueEffects {

  constructor(
    private readonly actions$: Actions,
    private readonly catalogueService: CatalogueService,
  ){
  }

  listenToCatalogue$ = createEffect(() => this.actions$.pipe(
    ofType(actions.listenToCatalogue),
    switchMap(() => timer(0, 5000).pipe(
      map(() => actions.refreshCatalogue())
    )),
  ));

  refreshCatalogue$ = createEffect(() => this.actions$.pipe(
    ofType(actions.refreshCatalogue),
    mergeMap(() => this.catalogueService.findAllDrinks()),
    map(items => actions.catalogue({items})),
  ));

}
