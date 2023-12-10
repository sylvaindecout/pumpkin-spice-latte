import {TestBed} from '@angular/core/testing';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {provideMockStore} from '@ngrx/store/testing';
import {AppComponent} from './app.component';
import {initialState} from './menu/state/menu.reducer';
import {OrderingModule} from './ordering/ordering.module';
import {PreparationModule} from './preparation/preparation.module';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      OrderingModule,
      PreparationModule,
      StoreModule.forRoot({}),
      EffectsModule.forRoot([]),
      HttpClientTestingModule,
      AppComponent,
    ],
    providers: [
      provideMockStore({initialState})
    ],
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
