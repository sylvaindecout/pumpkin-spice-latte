import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable, map } from 'rxjs';
import { DrinkInPreparation } from '../drink-in-preparation';
import { listenToDrinksInPreparation } from '../state/preparation.actions';
import { selectDrinksInPreparation } from '../state/preparation.reducer';
import { NgIf, NgFor, AsyncPipe } from '@angular/common';

@Component({
  selector: 'preparation-list',
  templateUrl: './preparation-list.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgFor,
    AsyncPipe,
  ],
})
export class PreparationListComponent {

  readonly drinks$: Observable<DrinkInPreparation[]>;

  constructor(
    private readonly store: Store,
  ) {
    this.drinks$ = store.select(selectDrinksInPreparation);
    store.dispatch(listenToDrinksInPreparation());
  }

  get noDrinks$(): Observable<boolean> {
    return this.drinks$.pipe(map(list => list.length < 1));
  }

 }
