import {Action, ActionsSubject} from '@ngrx/store';
import {Observable, of} from 'rxjs';
import {DrinkInPreparation} from '../drink-in-preparation';
import {PreparationService} from '../preparation.service';
import {drinksInPreparation, refreshDrinksInPreparation} from './preparation.actions';
import {PreparationEffects} from './preparation.effects';

describe('InvoicesEffects', () => {

  const actions$ = new ActionsSubject();
  const preparationServiceMock = <PreparationService>{
    findAll(): Observable<DrinkInPreparation[]> {
      return of();
    }
  };
  const effects = new PreparationEffects(actions$, preparationServiceMock);

  it('should call API and trigger drinksInPreparation action on refreshDrinksInPreparation action', () => {
    const drinks: DrinkInPreparation[] = [{
      name: 'Latte',
      customer: 'Giorgio',
    }];
    spyOn(preparationServiceMock, 'findAll').and.returnValue(of(drinks));
    const result: Action[] = [];
    effects.refreshDrinksInPreparation$.subscribe((action) => result.push(action));

    actions$.next(refreshDrinksInPreparation());

    expect(result).toEqual([drinksInPreparation({drinks})]);
  });

});
