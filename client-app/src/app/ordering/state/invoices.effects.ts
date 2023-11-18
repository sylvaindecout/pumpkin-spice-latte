import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { map, mergeMap } from 'rxjs';
import { OrderingService } from '../ordering.service';
import * as actions from './invoices.actions';

@Injectable()
export class InvoicesEffects {

  constructor(
    private readonly actions$: Actions,
    private readonly orderingService: OrderingService,
  ){
  }

  sendOrder$ = createEffect(() => this.actions$.pipe(
    ofType(actions.sendOrder),
    mergeMap(action => this.orderingService.process(action.order)),
    map(invoice => actions.newInvoice({invoice})),
  ));

}
