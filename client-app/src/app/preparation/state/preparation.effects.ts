import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, mergeMap, timer } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { PreparationService } from '../preparation.service';
import * as actions from './preparation.actions';

@Injectable()
export class PreparationEffects {

  constructor(
    private readonly actions$: Actions,
    private readonly preparationService: PreparationService,
  ){
  }

  listenToDrinksInPreparation$ = createEffect(() => this.actions$.pipe(
    ofType(actions.listenToDrinksInPreparation),
    switchMap(() => timer(0, 5000).pipe(
      map(() => actions.refreshDrinksInPreparation())
    )),
  ));

  refreshDrinksInPreparation$ = createEffect(() => this.actions$.pipe(
    ofType(actions.refreshDrinksInPreparation),
    mergeMap(() => this.preparationService.findAll()),
    map(drinks => actions.drinksInPreparation({drinks})),
  ));

}
