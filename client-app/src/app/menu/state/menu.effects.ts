import {Injectable} from '@angular/core';
import {Actions, createEffect, ofType} from '@ngrx/effects';
import {map, mergeMap, timer} from 'rxjs';
import {switchMap} from 'rxjs/operators';
import {MenuService} from '../menu.service';
import * as actions from './menu.actions';

@Injectable()
export class MenuEffects {

  constructor(
    private readonly actions$: Actions,
    private readonly menuService: MenuService,
  ) {
  }

  listenToMenu$ = createEffect(() => this.actions$.pipe(
    ofType(actions.listenToMenu),
    switchMap(() => timer(0, 5000).pipe(
      map(() => actions.refreshMenu())
    )),
  ));

  refreshMenu$ = createEffect(() => this.actions$.pipe(
    ofType(actions.refreshMenu),
    mergeMap(() => this.menuService.findAllDrinks()),
    map(items => actions.menu({items})),
  ));

}
