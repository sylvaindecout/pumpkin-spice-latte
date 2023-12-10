import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import {listenToMenu} from './menu/state/menu.actions';
import {PreparationListComponent} from './preparation/preparation-list/preparation-list.component';
import {OrderingComponent} from './ordering/ordering/ordering.component';
import {NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  imports: [OrderingComponent, PreparationListComponent, NgOptimizedImage],
})
export class AppComponent {

  constructor(
    private readonly store: Store,
  ) {
    store.dispatch(listenToMenu());
  }

}
