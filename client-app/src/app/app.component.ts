import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { listenToCatalogue } from './catalogue/state/catalogue.actions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent {

  constructor(
    private readonly store: Store,
  ) {
    store.dispatch(listenToCatalogue());
  }

}
